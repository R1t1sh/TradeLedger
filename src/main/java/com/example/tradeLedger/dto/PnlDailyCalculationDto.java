package com.example.tradeLedger.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PnlDailyCalculationDto {

    private LocalDate tradeDate;
    private Integer tradingDaySequence;
    private Integer remainingTradingDays;
    private BigDecimal dailyPlan;
    private BigDecimal actualPnl;
    private BigDecimal xtraShortfall;
    private BigDecimal mtdAchieved;
    private BigDecimal ytaMonth;
    private BigDecimal mtdPct;
    private String remark;

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getTradingDaySequence() {
        return tradingDaySequence;
    }

    public void setTradingDaySequence(Integer tradingDaySequence) {
        this.tradingDaySequence = tradingDaySequence;
    }

    public Integer getRemainingTradingDays() {
        return remainingTradingDays;
    }

    public void setRemainingTradingDays(Integer remainingTradingDays) {
        this.remainingTradingDays = remainingTradingDays;
    }

    public BigDecimal getDailyPlan() {
        return dailyPlan;
    }

    public void setDailyPlan(BigDecimal dailyPlan) {
        this.dailyPlan = dailyPlan;
    }

    public BigDecimal getActualPnl() {
        return actualPnl;
    }

    public void setActualPnl(BigDecimal actualPnl) {
        this.actualPnl = actualPnl;
    }

    public BigDecimal getXtraShortfall() {
        return xtraShortfall;
    }

    public void setXtraShortfall(BigDecimal xtraShortfall) {
        this.xtraShortfall = xtraShortfall;
    }

    public BigDecimal getMtdAchieved() {
        return mtdAchieved;
    }

    public void setMtdAchieved(BigDecimal mtdAchieved) {
        this.mtdAchieved = mtdAchieved;
    }

    public BigDecimal getYtaMonth() {
        return ytaMonth;
    }

    public void setYtaMonth(BigDecimal ytaMonth) {
        this.ytaMonth = ytaMonth;
    }

    public BigDecimal getMtdPct() {
        return mtdPct;
    }

    public void setMtdPct(BigDecimal mtdPct) {
        this.mtdPct = mtdPct;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
