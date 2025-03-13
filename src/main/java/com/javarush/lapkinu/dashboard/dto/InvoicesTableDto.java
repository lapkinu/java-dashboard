package com.javarush.lapkinu.dashboard.dto;

import java.time.LocalDate;
import java.util.UUID;

public record InvoicesTableDto(
        UUID id,
        UUID customerId,
        String name,
        String email,
        String imageUrl,
        LocalDate date,
        double amount,
        String status
) {}