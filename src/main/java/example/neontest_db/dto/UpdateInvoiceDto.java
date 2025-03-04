package example.neontest_db.dto;

import java.util.UUID;

public record UpdateInvoiceDto(
        UUID customerId,
        Double amount,
        String status
) {}
