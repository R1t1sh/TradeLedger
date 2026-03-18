package com.example.tradeLedger.controller;

import com.example.tradeLedger.dto.PanUpdateRequest;
import com.example.tradeLedger.entity.GoogleToken;
import com.example.tradeLedger.repository.GoogleTokenRepository;
import com.example.tradeLedger.utils.CryptoUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final GoogleTokenRepository tokenRepository;

    public UserController(GoogleTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/update-pan")
    public ResponseEntity<?> updatePan(@RequestBody PanUpdateRequest request,
                                       Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = (String) authentication.getPrincipal();

        System.out.println("EMAIL IN CONTROLLER: " + email);

        GoogleToken user = tokenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String pan = request.getPanCard();

        System.out.println(pan);
        // ✅ VALIDATE PAN
        if (!pan.toLowerCase().matches("[a-z]{5}[0-9]{4}[a-z]{1}")) {
            return ResponseEntity.badRequest().body("Invalid PAN format");
        }

        // 🔐 ENCRYPT PAN
        String encryptedPan = CryptoUtil.encrypt(pan.toLowerCase());

        user.setPanCard(encryptedPan);
        tokenRepository.save(user);

        return ResponseEntity.ok("PAN updated securely");
    }
}