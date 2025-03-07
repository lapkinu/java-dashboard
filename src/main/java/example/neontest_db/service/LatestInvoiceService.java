package example.neontest_db.service;

import example.neontest_db.dto.LatestInvoiceDto;
import example.neontest_db.entity.Invoice;
import example.neontest_db.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LatestInvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<LatestInvoiceDto> getLatestInvoices() {
        return invoiceRepository.findTop5ByOrderByDateDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private LatestInvoiceDto toDto(Invoice invoice) {
        return new LatestInvoiceDto(
                invoice.getId(),
                invoice.getCustomer().getName(),
                invoice.getCustomer().getImageUrl(),
                invoice.getCustomer().getEmail(),
                invoice.getAmount() / 100.0 // Предполагаем, что сумма хранится в центах
        );
    }
}