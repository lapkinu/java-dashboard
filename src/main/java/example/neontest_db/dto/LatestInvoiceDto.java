package example.neontest_db.dto;

import java.util.UUID;

public record LatestInvoiceDto(
        UUID id,
        String name,
        String imageUrl,
        String email,
        double amount
) {}