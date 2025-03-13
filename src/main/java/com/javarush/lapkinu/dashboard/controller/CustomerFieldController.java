package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.CustomerFieldDto;
import com.javarush.lapkinu.dashboard.service.CustomerFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer-fields")
@RequiredArgsConstructor
public class CustomerFieldController {

    private final CustomerFieldService customerFieldService;

    @GetMapping
    public ResponseEntity<List<CustomerFieldDto>> getCustomersForForm() {
        return ResponseEntity.ok(customerFieldService.getCustomersForForm());
    }
}