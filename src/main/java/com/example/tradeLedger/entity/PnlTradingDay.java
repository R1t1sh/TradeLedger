package com.example.tradeLedger.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "pnl_trading_day",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_pnl_trading_day_date", columnNames = {"plan_month_id", "trade_date"}),
                @UniqueConstraint(name = "uq_pnl_trading_day_sequence", columnNames = {"plan_month_id", "trading_day_sequence"})
        }
)
public class PnlTradingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_month_id", nullable = false)
    private PnlPlanMonth planMonth;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "trading_day_sequence", nullable = false)
    private Integer tradingDaySeq;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(name = "daily_target", precision = 18, scale = 2)
    private BigDecimal dailyTarget;

    @Column(name = "daily_target_updated_at")
    private LocalDateTime dailyTargetUpdatedAt;

    @OneToOne(mappedBy = "tradingDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PnlDailyFact dailyFact;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public PnlTradingDay() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PnlPlanMonth getPlanMonth() { return planMonth; }
    public void setPlanMonth(PnlPlanMonth planMonth) { this.planMonth = planMonth; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    public Integer getTradingDaySeq() { return tradingDaySeq; }
    public void setTradingDaySeq(Integer tradingDaySeq) { this.tradingDaySeq = tradingDaySeq; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public BigDecimal getDailyTarget() { return dailyTarget; }
    public void setDailyTarget(BigDecimal dailyTarget) { this.dailyTarget = dailyTarget; }

    public LocalDateTime getDailyTargetUpdatedAt() { return dailyTargetUpdatedAt; }
    public void setDailyTargetUpdatedAt(LocalDateTime dailyTargetUpdatedAt) { this.dailyTargetUpdatedAt = dailyTargetUpdatedAt; }

    public PnlDailyFact getDailyFact() { return dailyFact; }
    public void setDailyFact(PnlDailyFact dailyFact) { this.dailyFact = dailyFact; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
