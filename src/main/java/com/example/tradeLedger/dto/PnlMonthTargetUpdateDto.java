package com.example.tradeLedger.dto;

import java.math.BigDecimal;

public class PnlMonthTargetUpdateDto {

    private BigDecimal manualTarget;

    public BigDecimal getManualTarget() {
        return manualTarget;
    }

    public void setManualTarget(BigDecimal manualTarget) {
        this.manualTarget = manualTarget;
    }
}
