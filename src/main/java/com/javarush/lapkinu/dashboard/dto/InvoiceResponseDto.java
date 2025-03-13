package com.javarush.lapkinu.dashboard.dto;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceResponseDto(
        UUID id,
        UUID customerId,
        double amount,
        String status,
        LocalDate date
) {}