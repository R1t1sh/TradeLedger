package com.example.tradeLedger.dto;

import java.math.BigDecimal;

public class PnlMonthSummaryDto {

    private Integer monthSequence;
    private String monthLabel;
    private Integer tradingDaysPlanned;
    private BigDecimal monthTarget;
    private BigDecimal monthAchieved;
    private BigDecimal monthBalance;
    private BigDecimal monthAchievedPct;
    private BigDecimal ytdAchieved;
    private BigDecimal ytdBalance;

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

    public Integer getTradingDaysPlanned() {
        return tradingDaysPlanned;
    }

    public void setTradingDaysPlanned(Integer tradingDaysPlanned) {
        this.tradingDaysPlanned = tradingDaysPlanned;
    }

    public BigDecimal getMonthTarget() {
        return monthTarget;
    }

    public void setMonthTarget(BigDecimal monthTarget) {
        this.monthTarget = monthTarget;
    }

    public BigDecimal getMonthAchieved() {
        return monthAchieved;
    }

    public void setMonthAchieved(BigDecimal monthAchieved) {
        this.monthAchieved = monthAchieved;
    }

    public BigDecimal getMonthBalance() {
        return monthBalance;
    }

    public void setMonthBalance(BigDecimal monthBalance) {
        this.monthBalance = monthBalance;
    }

    public BigDecimal getMonthAchievedPct() {
        return monthAchievedPct;
    }

    public void setMonthAchievedPct(BigDecimal monthAchievedPct) {
        this.monthAchievedPct = monthAchievedPct;
    }

    public BigDecimal getYtdAchieved() {
        return ytdAchieved;
    }

    public void setYtdAchieved(BigDecimal ytdAchieved) {
        this.ytdAchieved = ytdAchieved;
    }

    public BigDecimal getYtdBalance() {
        return ytdBalance;
    }

    public void setYtdBalance(BigDecimal ytdBalance) {
        this.ytdBalance = ytdBalance;
    }
}
