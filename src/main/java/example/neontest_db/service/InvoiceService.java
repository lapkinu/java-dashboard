package example.neontest_db.service;

import example.neontest_db.dto.CreateInvoiceDto;
import example.neontest_db.dto.InvoiceResponseDto;
import example.neontest_db.dto.UpdateInvoiceDto;
import example.neontest_db.entity.Customer;
import example.neontest_db.entity.Invoice;
import example.neontest_db.entity.InvoiceStatus;
import example.neontest_db.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    @Transactional
    public InvoiceResponseDto createInvoice(CreateInvoiceDto dto) {
        Invoice invoice = new Invoice();
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        invoice.setCustomer(customer);
        invoice.setAmount((int) (dto.amount() * 100));
        invoice.setStatus(InvoiceStatus.valueOf(dto.status().toUpperCase()));
        invoice.setDate(LocalDate.now());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return toDto(savedInvoice);
    }

    @Transactional
    public InvoiceResponseDto updateInvoice(UUID id, UpdateInvoiceDto dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        if (dto.customerId() != null) {
            Customer customer = customerRepository.findById(dto.customerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            invoice.setCustomer(customer);
        }
        if (dto.amount() != null) {
            invoice.setAmount((int) (dto.amount() * 100));
        }
        if (dto.status() != null) {
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
                invoice.getCustomer().getId(),
                invoice.getAmount() / 100.0,
                invoice.getStatus().name(),
                invoice.getDate()
        );
    }
}