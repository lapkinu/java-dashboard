package com.javarush.lapkinu.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateInvoiceDto(
        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @Positive(message = "Amount must be positive")
        double amount,

        @NotBlank(message = "Status is required")
        String status
) {}