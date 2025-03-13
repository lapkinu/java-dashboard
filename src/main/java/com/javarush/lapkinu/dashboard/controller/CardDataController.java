package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.CardDataDto;
import com.javarush.lapkinu.dashboard.service.CardDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/card-data")
@RequiredArgsConstructor
public class CardDataController {

    private final CardDataService cardDataService;

    @GetMapping
    public ResponseEntity<CardDataDto> getCardData() {
        return ResponseEntity.ok(cardDataService.getCardData());
    }
}