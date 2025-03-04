package example.neontest_db.dto;

import example.neontest_db.entity.InvoiceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateInvoiceDto(
        UUID customerId,
        Double amount,
        String status
) {}
