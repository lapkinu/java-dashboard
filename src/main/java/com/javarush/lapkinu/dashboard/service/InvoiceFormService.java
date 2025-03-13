package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.InvoiceFormDto;
import com.javarush.lapkinu.dashboard.entity.Invoice;
import com.javarush.lapkinu.dashboard.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceFormService {

    private final InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public InvoiceFormDto getInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
        return toDto(invoice);
    }

    private InvoiceFormDto toDto(Invoice invoice) {
        return new InvoiceFormDto(
                invoice.getId(),
                invoice.getCustomer().getId(),
                invoice.getAmount() / 100.0,
                invoice.getStatus().name()
        );
    }
}