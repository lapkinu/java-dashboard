package example.neontest_db.service;

import example.neontest_db.dto.CreateInvoiceDto;
import example.neontest_db.dto.InvoiceResponseDto;
import example.neontest_db.dto.UpdateInvoiceDto;
import example.neontest_db.entity.Invoice;
import example.neontest_db.entity.InvoiceStatus;
import example.neontest_db.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public InvoiceResponseDto createInvoice(CreateInvoiceDto dto) {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(dto.customerId()); // Было getCustomerId()
        invoice.setAmount((int) (dto.amount() * 100)); // Было getAmount()
        invoice.setStatus(InvoiceStatus.valueOf(dto.status().toUpperCase())); // Было getStatus()
        invoice.setDate(LocalDate.now());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return toDto(savedInvoice);
    }

    @Transactional
    public InvoiceResponseDto updateInvoice(UUID id, UpdateInvoiceDto dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        if (dto.customerId() != null) { // Было getCustomerId()
            invoice.setCustomerId(dto.customerId());
        }
        if (dto.amount() != null) { // Было getAmount()
            invoice.setAmount((int) (dto.amount() * 100));
        }
        if (dto.status() != null) { // Было getStatus()
            invoice.setStatus(InvoiceStatus.valueOf(dto.status().toUpperCase()));
        }

        return toDto(invoiceRepository.save(invoice));
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public void deleteInvoice(UUID id) {
        invoiceRepository.deleteById(id);
    }

    private InvoiceResponseDto toDto(Invoice invoice) {
        return new InvoiceResponseDto(
                invoice.getId(),
                invoice.getCustomerId(),
                invoice.getAmount() / 100.0, // Обратная конвертация
                invoice.getStatus().name(),
                invoice.getDate()
        );
    }
}