package com.example.tradeLedger.dto;

import java.time.LocalDate;
import java.util.List;

public class PnlProcessResultDto {

    private boolean persisted;
    private boolean duplicateImport;
    private String planName;
    private String monthLabel;
    private LocalDate tradeDate;
    private Long importSourceId;
    private PnlMonthSummaryDto currentMonthSummary;
    private List<PnlMonthSummaryDto> yearSummary;
    private List<PnlDailyCalculationDto> dailySheet;

    public boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    public boolean isDuplicateImport() {
        return duplicateImport;
    }

    public void setDuplicateImport(boolean duplicateImport) {
        this.duplicateImport = duplicateImport;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
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

    public Long getImportSourceId() {
        return importSourceId;
    }

    public void setImportSourceId(Long importSourceId) {
        this.importSourceId = importSourceId;
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
