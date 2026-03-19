drop view if exists vw_pnl_daily_sheet;
drop view if exists vw_pnl_month_summary;

drop table if exists pnl_obligation_snapshot cascade;
drop table if exists pnl_import_source cascade;
drop table if exists pnl_trading_day cascade;
drop table if exists pnl_plan_month cascade;
drop table if exists pnl_plan cascade;
drop table if exists trading_calendar cascade;

-- After dropping, run pnl-normalized-schema.sql
