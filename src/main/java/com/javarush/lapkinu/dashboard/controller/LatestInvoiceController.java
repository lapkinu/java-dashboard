package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.LatestInvoiceDto;
import com.javarush.lapkinu.dashboard.service.LatestInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/latest-invoices")
@RequiredArgsConstructor
public class LatestInvoiceController {

    private final LatestInvoiceService latestInvoiceService;

    @GetMapping
    public ResponseEntity<List<LatestInvoiceDto>> getLatestInvoices() {
        return ResponseEntity.ok(latestInvoiceService.getLatestInvoices());
    }
}