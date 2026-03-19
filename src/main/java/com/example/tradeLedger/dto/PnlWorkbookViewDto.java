package com.example.tradeLedger.dto;

import java.time.LocalDate;
import java.util.List;

public class PnlWorkbookViewDto {

    private PnlPlanDto plan;
    private String monthLabel;
    private LocalDate tradeDate;
    private PnlMonthSummaryDto currentMonthSummary;
    private List<PnlMonthSummaryDto> yearSummary;
    private List<PnlDailyCalculationDto> dailySheet;

    public PnlPlanDto getPlan() {
        return plan;
    }

    public void setPlan(PnlPlanDto plan) {
        this.plan = plan;
    }

    public String getMonthLabel() {
        return monthLabel;
    }

    public void setMonthLabel(String monthLabel) {
        this.monthLabel = monthLabel;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public PnlMonthSummaryDto getCurrentMonthSummary() {
        return currentMonthSummary;
    }

    public void setCurrentMonthSummary(PnlMonthSummaryDto currentMonthSummary) {
        this.currentMonthSummary = currentMonthSummary;
    }

    public List<PnlMonthSummaryDto> getYearSummary() {
        return yearSummary;
    }

    public void setYearSummary(List<PnlMonthSummaryDto> yearSummary) {
        this.yearSummary = yearSummary;
    }

    public List<PnlDailyCalculationDto> getDailySheet() {
        return dailySheet;
    }

    public void setDailySheet(List<PnlDailyCalculationDto> dailySheet) {
        this.dailySheet = dailySheet;
    }
}
