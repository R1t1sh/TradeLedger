create table if not exists pnl_plan (
    id bigserial primary key,
    user_id bigint not null references user_details(id),
    plan_name varchar(100) not null,
    start_date date not null,
    end_date date not null,
    annual_target numeric(18, 2) not null check (annual_target >= 0),
    currency_code char(3) not null default 'INR',
    active boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint uq_pnl_plan_user_name unique (user_id, plan_name),
    constraint ck_pnl_plan_dates check (start_date <= end_date)
);

create index if not exists idx_pnl_plan_user_id on pnl_plan(user_id);

create table if not exists pnl_plan_month (
    id bigserial primary key,
    plan_id bigint not null references pnl_plan(id) on delete cascade,
    month_sequence smallint not null check (month_sequence > 0),
    month_label varchar(20) not null,
    month_start_date date not null,
    month_end_date date not null,
    trading_days_planned smallint not null check (trading_days_planned > 0),
    allocated_target numeric(18, 2),
    manual_target_override numeric(18, 2),
    target_calculated_at timestamptz,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint uq_pnl_plan_month_sequence unique (plan_id, month_sequence),
    constraint uq_pnl_plan_month_start unique (plan_id, month_start_date),
    constraint ck_pnl_plan_month_dates check (month_start_date <= month_end_date)
);

create index if not exists idx_pnl_plan_month_plan_id on pnl_plan_month(plan_id);

create table if not exists pnl_trading_day (
    id bigserial primary key,
    plan_month_id bigint not null references pnl_plan_month(id) on delete cascade,
    trade_date date not null,
    trading_day_sequence smallint not null check (trading_day_sequence > 0),
    daily_target numeric(18, 2),
    daily_target_updated_at timestamptz,
    remark text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint uq_pnl_trading_day_date unique (plan_month_id, trade_date),
    constraint uq_pnl_trading_day_sequence unique (plan_month_id, trading_day_sequence)
);

create index if not exists idx_pnl_trading_day_plan_month_id on pnl_trading_day(plan_month_id);
create index if not exists idx_pnl_trading_day_trade_date on pnl_trading_day(trade_date);

create table if not exists pnl_import_source (
    id bigserial primary key,
    user_id bigint not null references user_details(id),
    trade_date date not null,
    gmail_message_id varchar(255) not null,
    attachment_checksum varchar(64),
    processing_status varchar(20) not null default 'PROCESSED',
    processed_at timestamptz not null default now(),
    created_at timestamptz not null default now(),
    constraint uq_pnl_import_message unique (user_id, gmail_message_id),
    constraint uq_pnl_import_trade_checksum unique (user_id, trade_date, attachment_checksum),
    constraint ck_pnl_import_status check (processing_status in ('PENDING', 'PROCESSED', 'FAILED', 'SKIPPED'))
);

create index if not exists idx_pnl_import_source_user_trade_date on pnl_import_source(user_id, trade_date);

create table if not exists pnl_obligation_snapshot (
    id bigserial primary key,
    trading_day_id bigint not null references pnl_trading_day(id) on delete cascade,
    import_source_id bigint references pnl_import_source(id) on delete set null,
    pay_in_pay_out numeric(18, 2),
    brokerage numeric(18, 2),
    transaction_charges numeric(18, 2),
    net_amount numeric(18, 2) not null,
    no_of_trades integer,
    extracted_at timestamptz not null default now(),
    created_at timestamptz not null default now(),
    constraint uq_pnl_obligation_trading_day unique (trading_day_id)
);

create index if not exists idx_pnl_obligation_snapshot_import_source_id on pnl_obligation_snapshot(import_source_id);

create or replace view vw_pnl_month_summary as
with month_actuals as (
    select
        pm.id as plan_month_id,
        pm.plan_id,
        pm.month_sequence,
        pm.month_label,
        pm.month_start_date,
        pm.month_end_date,
        pm.trading_days_planned,
        pm.allocated_target,
        pm.manual_target_override,
        coalesce(sum(pos.net_amount), 0)::numeric(18, 2) as month_achieved
    from pnl_plan_month pm
    left join pnl_trading_day td on td.plan_month_id = pm.id
    left join pnl_obligation_snapshot pos on pos.trading_day_id = td.id
    group by
        pm.id,
        pm.plan_id,
        pm.month_sequence,
        pm.month_label,
        pm.month_start_date,
        pm.month_end_date,
        pm.trading_days_planned,
        pm.allocated_target,
        pm.manual_target_override
),
month_targeted as (
    select
        p.id as plan_id,
        p.user_id,
        ma.plan_month_id,
        ma.month_sequence,
        ma.month_label,
        ma.month_start_date,
        ma.month_end_date,
        ma.trading_days_planned,
        p.annual_target,
        ma.month_achieved,
        coalesce(ma.manual_target_override, ma.allocated_target, 0)::numeric(18, 2) as month_target
    from month_actuals ma
    join pnl_plan p on p.id = ma.plan_id
)
select
    mt.plan_id,
    mt.user_id,
    mt.plan_month_id,
    mt.month_sequence,
    mt.month_label,
    mt.month_start_date,
    mt.month_end_date,
    mt.trading_days_planned,
    mt.annual_target,
    mt.month_target,
    mt.month_achieved,
    round(mt.month_target - mt.month_achieved, 2)::numeric(18, 2) as month_balance,
    case
        when mt.month_target = 0 then null
        else round(mt.month_achieved / mt.month_target, 6)
    end as month_achieved_pct,
    round(
        sum(mt.month_achieved) over (
            partition by mt.plan_id
            order by mt.month_sequence
            rows between unbounded preceding and current row
        ),
        2
    )::numeric(18, 2) as ytd_achieved,
    round(
        mt.annual_target - sum(mt.month_achieved) over (
            partition by mt.plan_id
            order by mt.month_sequence
            rows between unbounded preceding and current row
        ),
        2
    )::numeric(18, 2) as ytd_balance
from month_targeted mt;

create or replace view vw_pnl_daily_sheet as
with month_summary as (
    select
        vms.plan_id,
        vms.user_id,
        vms.plan_month_id,
        vms.month_sequence,
        vms.month_label,
        vms.month_start_date,
        vms.month_end_date,
        vms.trading_days_planned,
        vms.month_target,
        vms.annual_target,
        vms.ytd_achieved,
        vms.ytd_balance
    from vw_pnl_month_summary vms
),
day_base as (
    select
        td.id as trading_day_id,
        ms.plan_id,
        ms.user_id,
        ms.plan_month_id,
        ms.month_sequence,
        ms.month_label,
        td.trade_date,
        td.trading_day_sequence,
        (ms.trading_days_planned - td.trading_day_sequence + 1) as remaining_trading_days,
        ms.trading_days_planned,
        ms.month_target,
        td.daily_target,
        ms.annual_target,
        ms.ytd_achieved,
        ms.ytd_balance,
        td.remark,
        pos.net_amount as actual_pnl
    from pnl_trading_day td
    join month_summary ms on ms.plan_month_id = td.plan_month_id
    left join pnl_obligation_snapshot pos on pos.trading_day_id = td.id
),
day_rollup as (
    select
        db.*,
        coalesce(
            sum(coalesce(db.actual_pnl, 0)) over (
                partition by db.plan_month_id
                order by db.trading_day_sequence
                rows between unbounded preceding and 1 preceding
            ),
            0
        )::numeric(18, 2) as achieved_before_day,
        sum(case when db.actual_pnl is null then 1 else 0 end) over (
            partition by db.plan_month_id
            order by db.trading_day_sequence
            rows between unbounded preceding and 1 preceding
        ) as missing_before_day,
        sum(coalesce(db.actual_pnl, 0)) over (
            partition by db.plan_month_id
            order by db.trading_day_sequence
            rows between unbounded preceding and current row
        )::numeric(18, 2) as mtd_achieved_raw
    from day_base db
),
day_planned as (
    select
        dr.*,
        case
            when dr.trading_day_sequence = 1 then round(dr.month_target / nullif(dr.trading_days_planned, 0), 2)
            when dr.missing_before_day = 0 then round((dr.month_target - dr.achieved_before_day) / nullif(dr.remaining_trading_days, 0), 2)
            else null
        end::numeric(18, 2) as daily_plan
    from day_rollup dr
)
select
    dp.plan_id,
    dp.user_id,
    dp.plan_month_id,
    dp.month_sequence,
    dp.month_label,
    dp.trading_day_id,
    dp.trade_date,
    dp.trading_day_sequence,
    dp.remaining_trading_days,
    dp.trading_days_planned,
    dp.month_target,
    coalesce(dp.daily_target, dp.daily_plan)::numeric(18, 2) as daily_plan,
    dp.actual_pnl,
    case
        when dp.actual_pnl is null or coalesce(dp.daily_target, dp.daily_plan) is null then null
        else round(dp.actual_pnl - coalesce(dp.daily_target, dp.daily_plan), 2)
    end::numeric(18, 2) as xtra_shortfall,
    case
        when dp.actual_pnl is null then null
        else dp.mtd_achieved_raw
    end as mtd_achieved,
    case
        when dp.actual_pnl is null then null
        else round(dp.month_target - dp.mtd_achieved_raw, 2)
    end::numeric(18, 2) as yta_month,
    case
        when dp.actual_pnl is null or dp.month_target = 0 then null
        else round(dp.mtd_achieved_raw / dp.month_target, 6)
    end as mtd_pct,
    dp.annual_target,
    dp.ytd_achieved,
    dp.ytd_balance,
    dp.remark
from day_planned dp
order by dp.plan_id, dp.month_sequence, dp.trading_day_sequence;
