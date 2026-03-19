package com.example.tradeLedger.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "pnl_plan_month",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_pnl_plan_month_sequence", columnNames = {"plan_id", "month_sequence"}),
                @UniqueConstraint(name = "uq_pnl_plan_month_start", columnNames = {"plan_id", "month_start_date"})
        }
)
public class PnlPlanMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private PnlPlan plan;

    @Column(name = "month_sequence", nullable = false)
    private Integer monthSequence;

    @Column(name = "month_label", nullable = false, length = 20)
    private String monthLabel;

    @Column(name = "month_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "month_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "trading_days_planned", nullable = false)
    private Integer tradingDaysPlanned;

    @Column(name = "manual_target_override", precision = 18, scale = 2)
    private BigDecimal manualTarget;

    @Column(name = "allocated_target", precision = 18, scale = 2)
    private BigDecimal allocatedTarget;

    @Column(name = "target_calculated_at")
    private LocalDateTime targetCalculatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "planMonth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PnlTradingDay> tradingDays;

    public PnlPlanMonth() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PnlPlan getPlan() { return plan; }
    public void setPlan(PnlPlan plan) { this.plan = plan; }

    public Integer getMonthSequence() { return monthSequence; }
    public void setMonthSequence(Integer monthSequence) { this.monthSequence = monthSequence; }

    public String getMonthLabel() { return monthLabel; }
    public void setMonthLabel(String monthLabel) { this.monthLabel = monthLabel; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getTradingDaysPlanned() { return tradingDaysPlanned; }
    public void setTradingDaysPlanned(Integer tradingDaysPlanned) { this.tradingDaysPlanned = tradingDaysPlanned; }

    public BigDecimal getManualTarget() { return manualTarget; }
    public void setManualTarget(BigDecimal manualTarget) { this.manualTarget = manualTarget; }

    public BigDecimal getAllocatedTarget() { return allocatedTarget; }
    public void setAllocatedTarget(BigDecimal allocatedTarget) { this.allocatedTarget = allocatedTarget; }

    public LocalDateTime getTargetCalculatedAt() { return targetCalculatedAt; }
    public void setTargetCalculatedAt(LocalDateTime targetCalculatedAt) { this.targetCalculatedAt = targetCalculatedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<PnlTradingDay> getTradingDays() { return tradingDays; }
    public void setTradingDays(List<PnlTradingDay> tradingDays) { this.tradingDays = tradingDays; }
}
