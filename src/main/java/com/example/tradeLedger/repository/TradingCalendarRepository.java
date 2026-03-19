package com.example.tradeLedger.repository;

import com.example.tradeLedger.entity.TradingCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TradingCalendarRepository extends JpaRepository<TradingCalendar, LocalDate> {

    List<TradingCalendar> findByTradeDateBetweenAndIsTradingDayTrueOrderByTradeDateAsc(LocalDate startDate, LocalDate endDate);
}
