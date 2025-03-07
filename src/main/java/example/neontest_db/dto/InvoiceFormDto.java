package example.neontest_db.dto;

import java.util.UUID;

public record InvoiceFormDto(
        UUID id,
        UUID customerId,
        double amount,
        String status
) {}