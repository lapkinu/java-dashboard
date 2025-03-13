package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.CustomersTableTypeDto;
import com.javarush.lapkinu.dashboard.service.FilteredCustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filtered-customers")
@RequiredArgsConstructor
public class FilteredCustomersController {

    private final FilteredCustomersService filteredCustomersService;

    @GetMapping
    public ResponseEntity<List<CustomersTableTypeDto>> getFilteredCustomers(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(filteredCustomersService.getFilteredCustomers(query));
    }
}