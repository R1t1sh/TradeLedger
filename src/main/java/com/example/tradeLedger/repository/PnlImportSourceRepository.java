package com.example.tradeLedger.repository;

import com.example.tradeLedger.entity.PnlImportSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PnlImportSourceRepository extends JpaRepository<PnlImportSource, Long> {

    Optional<PnlImportSource> findByUser_IdAndGmailMessageId(Long userId, String gmailMessageId);

    Optional<PnlImportSource> findByUser_IdAndTradeDateAndAttachmentChecksum(Long userId, LocalDate tradeDate, String attachmentChecksum);
}
