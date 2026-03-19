package com.example.tradeLedger.service;

import com.example.tradeLedger.entity.UserDetails;

public interface PdfProcessingService {

    String processPdf(String filePath, String password) throws Exception;

    String processPdf(String filePath, String password, UserDetails user, String gmailMessageId, String attachmentChecksum) throws Exception;
}
