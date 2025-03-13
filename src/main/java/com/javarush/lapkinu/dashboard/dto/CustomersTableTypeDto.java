package com.javarush.lapkinu.dashboard.dto;

import java.util.UUID;

public record CustomersTableTypeDto(
        UUID id,
        String name,
        String email,
        String imageUrl,
        long totalInvoices,
        double totalPending,
        double totalPaid
) {}