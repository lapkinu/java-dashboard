package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.InvoicesTableDto;
import com.javarush.lapkinu.dashboard.service.InvoicesTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filtered-invoices")
@RequiredArgsConstructor
public class InvoicesTableController {

    private final InvoicesTableService invoicesTableService;

    @GetMapping
    public ResponseEntity<List<InvoicesTableDto>> getFilteredInvoices(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int currentPage
    ) {
        return ResponseEntity.ok(invoicesTableService.getFilteredInvoices(query, currentPage));
    }

    @GetMapping("/pages")
    public ResponseEntity<Long> getTotalPages(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(invoicesTableService.getTotalPages(query));
    }
}