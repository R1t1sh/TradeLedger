package com.example.tradeLedger.controller;

import com.example.tradeLedger.entity.UserDetails;
import com.example.tradeLedger.repository.UserDetailsRepository;
import com.example.tradeLedger.service.PdfProcessingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {

    private final PdfProcessingService pdfProcessingService;
    private final UserDetailsRepository userDetailsRepository;

    public PdfController(PdfProcessingService pdfProcessingService, UserDetailsRepository userDetailsRepository) {
        this.pdfProcessingService = pdfProcessingService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("/process")
    public String processPdf(
            Authentication authentication,
            @RequestParam String path,
            @RequestParam String password
    ) throws Exception {

        if (authentication == null || authentication.getPrincipal() == null) {
            return pdfProcessingService.processPdf(path, password);
        }

        String userEmail = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsRepository.findByEmail(userEmail.trim().toLowerCase())
                .orElse(null);

        if (userDetails == null) {
            return pdfProcessingService.processPdf(path, password);
        }

        return pdfProcessingService.processPdf(path, password, userDetails, null, null);
    }
}
