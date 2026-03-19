# P&L Workbook Database Design

This workbook can be recreated without storing every Excel cell.

The only inputs that need to be persisted are:

- the yearly plan target
- the list of months inside that plan
- the trading dates for each month
- the actual daily P&L extracted from the obligation section
- optional remarks
- minimal Gmail import metadata to prevent duplicate processing

Everything else in the workbook should be derived in SQL or in the service layer.

## Workbook Mapping

| Excel area | Store in DB? | Where it belongs |
| --- | --- | --- |
| `YEAR TARGET` | Yes | `pnl_plan.annual_target` |
| `YTD ACHIEVED` | No | Derived from daily actual P&L |
| `YTD BALANCE` | No | `annual_target - ytd_achieved` |
| Monthly sheet name like `MAR26` | Yes | `pnl_plan_month.month_label` |
| Monthly trading days count | Yes | `pnl_plan_month.trading_days_planned` |
| Daily `Date` | Yes | `pnl_trading_day.trade_date` |
| Daily `Day (Actual)` | Yes | `pnl_obligation_snapshot.net_amount` |
| Daily `Remarks` | Yes | `pnl_trading_day.remark` |
| `Month` target | No by default | Derived from remaining annual balance / remaining months |
| `Day Plan (Balance Averaged)` | No | Derived from remaining monthly balance / remaining trading days |
| `Xtra / Short fall` | No | `actual_pnl - daily_plan` |
| `MTD` | No | Running sum inside the month |
| `YTA (Month)` | No | `month_target - mtd` |
| `MTD (%)` | No | `mtd / month_target` |
| Gmail message id / checksum | Yes, minimal only | `pnl_import_source` |
| Raw attachment / full PDF / full annexure rows | No | Not needed for this workbook |

## Normalized Model

### `pnl_plan`

One row per user per workbook plan, for example `TRACK_PNL_FY2026-27`.

Stores:

- plan label
- start and end date
- annual target
- currency

### `pnl_plan_month`

One row per plan month.

Stores:

- month order inside the plan
- month label like `MAR26`
- month start and end date
- number of trading days
- optional manual target override

The override is nullable because your Excel logic currently calculates month target automatically from the remaining annual balance.

### `pnl_trading_day`

One row per trading day in a month.

Stores:

- trade date
- sequence of the trading day inside the month
- optional remark

This is better than storing just `year`, `month`, and `day`, because the workbook is driven by actual trading days, not just calendar dates.

### `pnl_import_source`

One row per processed Gmail statement.

Stores only minimal ingestion metadata:

- user
- trade date
- Gmail message id
- attachment checksum
- processing timestamp
- processing status

This is enough for de-duplication and troubleshooting without storing the whole attachment.

### `pnl_obligation_snapshot`

One row per trading day after extracting the obligation section.

Stores:

- net amount
- pay in / pay out
- brokerage
- transaction charges
- number of trades if you want it

If you want the absolute minimum model, `net_amount` is the only mandatory figure for recreating the Excel sheet.

## Derived Formulas

The SQL view uses the same logic as the workbook:

- `month_target = (annual_target - achieved_before_month) / remaining_months`
- `daily_plan = (month_target - achieved_before_day) / remaining_trading_days`
- `xtra_shortfall = actual_pnl - daily_plan`
- `mtd = running_sum(actual_pnl within month)`
- `yta_month = month_target - mtd`
- `mtd_percent = mtd / month_target`
- `ytd_achieved = sum(all actual_pnl in the plan up to that month)`
- `ytd_balance = annual_target - ytd_achieved`

## Why This Is Better Than The Current Tables

The current `annual_target`, `monthly_target`, `daily_target`, and `trades` tables repeat the same time dimensions and also store values that are derived from each other.

That makes the model hard to keep consistent.

This schema removes those update problems by:

- storing only raw inputs
- separating plan structure from imported daily results
- deriving totals and percentages instead of persisting duplicate numbers

## Recommended UI Screens

- Year summary screen: read from `vw_pnl_month_summary`
- Monthly sheet screen: read from `vw_pnl_daily_sheet`
- Import history screen: read from `pnl_import_source`

## Important Practical Note

If the Gmail/PDF parser may process the same day multiple times, keep the unique constraints from the SQL file. They are important because they stop duplicate imports from inflating MTD and YTD.
