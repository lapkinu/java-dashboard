package example.neontest_db.dto;

public record CardDataDto(
        long numberOfCustomers,
        long numberOfInvoices,
        double totalPaidInvoices,
        double totalPendingInvoices
) {
}