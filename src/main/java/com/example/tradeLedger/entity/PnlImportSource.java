package com.example.tradeLedger.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "pnl_import_source",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_pnl_import_message", columnNames = {"user_id", "gmail_message_id"}),
                @UniqueConstraint(name = "uq_pnl_import_trade_checksum", columnNames = {"user_id", "trade_date", "attachment_checksum"})
        }
)
public class PnlImportSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails user;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "gmail_message_id", nullable = false)
    private String gmailMessageId;

    @Column(name = "attachment_checksum", length = 64)
    private String attachmentChecksum;

    @Column(name = "processing_status", nullable = false, length = 20)
    private String status = "PROCESSED";

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt = LocalDateTime.now();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PnlImportSource() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDetails getUser() { return user; }
    public void setUser(UserDetails user) { this.user = user; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    public String getGmailMessageId() { return gmailMessageId; }
    public void setGmailMessageId(String gmailMessageId) { this.gmailMessageId = gmailMessageId; }

    public String getAttachmentChecksum() { return attachmentChecksum; }
    public void setAttachmentChecksum(String attachmentChecksum) { this.attachmentChecksum = attachmentChecksum; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
