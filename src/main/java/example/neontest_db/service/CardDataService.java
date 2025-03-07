package example.neontest_db.service;

import example.neontest_db.dto.CardDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardDataService {

    private final InvoiceService invoiceService;
    private final CustomerService customerService;

    @Transactional(readOnly = true)
    public CardDataDto getCardData() {
        long numberOfInvoices = invoiceService.getAllInvoices().size();
        long numberOfCustomers = customerService.getAllCustomers().size();
        double totalPaidInvoices = invoiceService.getTotalPaidInvoices();
        double totalPendingInvoices = invoiceService.getTotalPendingInvoices();

        return new CardDataDto(
                numberOfCustomers,
                numberOfInvoices,
                totalPaidInvoices,
                totalPendingInvoices
        );
    }
}