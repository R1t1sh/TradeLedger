package com.example.tradeLedger.service;

public interface PnlScheduleService {

    void recalculateMonthlyTargets();

    void refreshDailyTargets();
}
