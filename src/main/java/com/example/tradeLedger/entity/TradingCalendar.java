package com.example.tradeLedger.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trading_calendar")
public class TradingCalendar {

    @Column(name = "trade_date")
    @Id
    private LocalDate tradeDate;

    @Column(nullable = false)
    private Boolean isTradingDay;

    private String holidayName;

    @Column(nullable = false)
    private Integer year;

    public TradingCalendar() {}

    // getters & setters
    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    public Boolean getIsTradingDay() { return isTradingDay; }
    public void setIsTradingDay(Boolean isTradingDay) { this.isTradingDay = isTradingDay; }

    public String getHolidayName() { return holidayName; }
    public void setHolidayName(String holidayName) { this.holidayName = holidayName; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
