package com.javarush.lapkinu.dashboard.dto;

public record CardDataDto(
        long numberOfCustomers,
        long numberOfInvoices,
        double totalPaidInvoices,
        double totalPendingInvoices
) {
}