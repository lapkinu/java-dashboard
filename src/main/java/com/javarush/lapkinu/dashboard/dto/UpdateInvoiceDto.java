package com.javarush.lapkinu.dashboard.dto;

import java.util.UUID;

public record UpdateInvoiceDto(
        UUID customerId,
        Double amount,
        String status
) {}
