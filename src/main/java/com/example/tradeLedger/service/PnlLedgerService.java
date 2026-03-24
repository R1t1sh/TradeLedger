package com.example.tradeLedger.service;

import com.example.tradeLedger.dto.ObligationDto;
import com.example.tradeLedger.dto.PnlDailyCalculationDto;
import com.example.tradeLedger.dto.PnlManualEntryRequestDto;
import com.example.tradeLedger.dto.PnlMonthSummaryDto;
import com.example.tradeLedger.dto.PnlMonthTargetUpdateDto;
import com.example.tradeLedger.dto.PnlPlanDto;
import com.example.tradeLedger.dto.PnlPlanRequestDto;
import com.example.tradeLedger.dto.PnlProcessResultDto;
import com.example.tradeLedger.dto.PnlWorkbookViewDto;
import com.example.tradeLedger.entity.UserDetails;

import java.time.LocalDate;
import java.util.List;

public interface PnlLedgerService {

    PnlProcessResultDto saveObligationAndCalculate(
            UserDetails user,
            LocalDate tradeDate,
            ObligationDto obligation,
            int numberOfTrades,
            String gmailMessageId,
            String attachmentChecksum
    );

    PnlPlanDto savePlan(UserDetails user, PnlPlanRequestDto request);

    List<PnlPlanDto> getPlans(UserDetails user);

    PnlPlanDto getActivePlan(UserDetails user, LocalDate tradeDate);

    PnlPlanDto updateMonthTarget(UserDetails user, Long monthId, PnlMonthTargetUpdateDto request);

    PnlProcessResultDto upsertManualDailyPnl(UserDetails user, PnlManualEntryRequestDto request);

    PnlWorkbookViewDto getWorkbookView(UserDetails user, LocalDate tradeDate);

    PnlMonthSummaryDto getCurrentMonthSummary(UserDetails user, LocalDate tradeDate);

    List<PnlMonthSummaryDto> getYearSummary(UserDetails user, LocalDate tradeDate);

    List<PnlDailyCalculationDto> getMonthSheet(UserDetails user, LocalDate tradeDate);

    void generateMonthlyStructure(LocalDate tradeDate);

    void ensureMonthlyStructure(UserDetails user, LocalDate tradeDate);

    void recalculateMonthlyTargets(LocalDate tradeDate);
}
