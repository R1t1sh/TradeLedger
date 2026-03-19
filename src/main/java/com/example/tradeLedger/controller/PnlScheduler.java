package com.example.tradeLedger.controller;

import com.example.tradeLedger.service.PnlScheduleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PnlScheduler {

    private final PnlScheduleService pnlScheduleService;

    public PnlScheduler(PnlScheduleService pnlScheduleService) {
        this.pnlScheduleService = pnlScheduleService;
    }

    @Scheduled(cron = "0 5 0 1 * *")
    public void recalculateMonthTargets() {
        pnlScheduleService.recalculateMonthlyTargets();
        pnlScheduleService.refreshDailyTargets();
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void refreshDailyTargets() {
        pnlScheduleService.refreshDailyTargets();
    }
}
