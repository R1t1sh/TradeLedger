package com.example.tradeLedger.controller;

import com.example.tradeLedger.entity.GoogleToken;
import com.example.tradeLedger.repository.GoogleTokenRepository;
import com.example.tradeLedger.serviceImpl.GmailService;
import com.example.tradeLedger.utils.CryptoUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GmailScheduler {

    private static final Logger log = LoggerFactory.getLogger(GmailScheduler.class);

    private final GoogleTokenRepository tokenRepository;
    private final GmailService gmailService;

    @Value("${google.client.id}")
    private String CLIENT_ID;

    @Value("${google.client.secret}")
    private String CLIENT_SECRET;

    public GmailScheduler(GoogleTokenRepository tokenRepository,
                          GmailService gmailService) {
        this.tokenRepository = tokenRepository;
        this.gmailService = gmailService;
    }

    // 🔥 Runs every 5 minutes
    @Scheduled(fixedDelay = 300000)
    public void processEmails() {

        log.info("🚀 Scheduler started...");

        List<GoogleToken> users = tokenRepository.findAll();

        if (users.isEmpty()) {
            log.info("⚠️ No users found");
            return;
        }

        for (GoogleToken user : users) {

            try {
                // 🔴 Skip revoked users
                if (user.isRevoked()) {
                    continue;
                }

                // 🔴 Skip if no refresh token
                if (user.getRefreshToken() == null) {
                    log.warn("⚠️ No refresh token for user: {}", user.getEmail());
                    continue;
                }

                // 🔐 Decrypt refresh token
                String refreshToken = CryptoUtil.decrypt(user.getRefreshToken());

                // 🔄 Get new Google access token
                String newAccessToken = new GoogleRefreshTokenRequest(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        refreshToken,
                        CLIENT_ID,
                        CLIENT_SECRET
                ).execute().getAccessToken();

                // 🔐 Encrypt and save access token
                user.setAccessToken(CryptoUtil.encrypt(newAccessToken));
                tokenRepository.save(user);

                // 📩 Read emails
                gmailService.readEmailsWithAttachments(
                        user.getEmail(),
                        "atulkumarsethi8@gmail.com" // or supplier email filter
                );

                log.info("✅ Successfully processed user: {}", user.getEmail());

            } catch (Exception e) {
                log.error("❌ Error processing user: {}", user.getEmail(), e);
            }
        }

        log.info("✅ Scheduler finished");
    }
}