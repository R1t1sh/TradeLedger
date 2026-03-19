package com.example.tradeLedger.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PnlPlanMonthDto {

    private Long id;
    private Integer monthSequence;
    private String monthLabel;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer tradingDaysPlanned;
    private BigDecimal allocatedTarget;
    private BigDecimal manualTarget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonthSequence() {
        return monthSequence;
    }

    public void setMonthSequence(Integer monthSequence) {
        this.monthSequence = monthSequence;
    }

    public String getMonthLabel() {
        return monthLabel;
    }

    public void setMonthLabel(String monthLabel) {
        this.monthLabel = monthLabel;
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

    public Integer getTradingDaysPlanned() {
        return tradingDaysPlanned;
    }

    public void setTradingDaysPlanned(Integer tradingDaysPlanned) {
        this.tradingDaysPlanned = tradingDaysPlanned;
    }

    public BigDecimal getAllocatedTarget() {
        return allocatedTarget;
    }

    public void setAllocatedTarget(BigDecimal allocatedTarget) {
        this.allocatedTarget = allocatedTarget;
    }

    public BigDecimal getManualTarget() {
        return manualTarget;
    }

    public void setManualTarget(BigDecimal manualTarget) {
        this.manualTarget = manualTarget;
    }
}
