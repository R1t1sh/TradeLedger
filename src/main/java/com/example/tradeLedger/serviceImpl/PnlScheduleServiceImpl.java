package com.example.tradeLedger.serviceImpl;

import com.example.tradeLedger.service.PnlLedgerService;
import com.example.tradeLedger.service.PnlScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PnlScheduleServiceImpl implements PnlScheduleService {

    private static final Logger log = LoggerFactory.getLogger(PnlScheduleServiceImpl.class);

    private final PnlLedgerService pnlLedgerService;

    public PnlScheduleServiceImpl(PnlLedgerService pnlLedgerService) {
        this.pnlLedgerService = pnlLedgerService;
    }

    @Override
    public void recalculateMonthlyTargets() {
        LocalDate today = LocalDate.now();
        log.info("Recalculating P&L monthly targets for {}", today);
        pnlLedgerService.recalculateMonthlyTargets(today);
    }

    @Override
    public void refreshDailyTargets() {
        LocalDate today = LocalDate.now();
        log.info("Refreshing P&L daily targets for {}", today);
        pnlLedgerService.generateMonthlyStructure(today);
    }
}
