package com.example.tradeLedger.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PnlPlanDto {

    private Long id;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal annualTarget;
    private String currency;
    private boolean active;
    private BigDecimal totalAchievedAmount;
    private List<PnlPlanMonthDto> months;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAnnualTarget() {
        return annualTarget;
    }

    public void setAnnualTarget(BigDecimal annualTarget) {
        this.annualTarget = annualTarget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PnlPlanMonthDto> getMonths() {
        return months;
    }

    public void setMonths(List<PnlPlanMonthDto> months) {
        this.months = months;
    }

    public BigDecimal getTotalAchievedAmount() {
        return totalAchievedAmount;
    }

    public void setTotalAchievedAmount(BigDecimal totalAchievedAmount) {
        this.totalAchievedAmount = totalAchievedAmount;
    }
}
