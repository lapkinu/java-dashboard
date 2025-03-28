package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CreateInvoiceDto;
import com.javarush.lapkinu.dashboard.dto.InvoiceResponseDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.entity.Invoice;
import com.javarush.lapkinu.dashboard.entity.InvoiceStatus;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import com.javarush.lapkinu.dashboard.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    private InvoiceService invoiceService;
    private InvoiceRepository invoiceRepository;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        customerRepository = mock(CustomerRepository.class);
        invoiceService = new InvoiceService(invoiceRepository, customerRepository);
    }

    @Test
    void createInvoice_success() {
        UUID customerId = UUID.randomUUID();
        CreateInvoiceDto dto = new CreateInvoiceDto(customerId, 100.0, "PENDING");
        Customer customer = new Customer();
        Invoice savedInvoice = new Invoice();
        savedInvoice.setId(UUID.randomUUID());
        savedInvoice.setCustomer(customer);
        savedInvoice.setAmount(10000); // 100.0 * 100
        savedInvoice.setStatus(InvoiceStatus.PENDING);
        savedInvoice.setDate(LocalDate.now());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);

        InvoiceResponseDto result = invoiceService.createInvoice(dto);

        assertNotNull(result);
        assertEquals(100.0, result.amount());
        assertEquals("PENDING", result.status());
        verify(invoiceRepository).save(any(Invoice.class));
    }

    @Test
    void getAllInvoices_success() {
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID());
        invoice.setCustomer(new Customer());
        invoice.setAmount(10000);
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setDate(LocalDate.now());

        when(invoiceRepository.findAll()).thenReturn(List.of(invoice));

        List<InvoiceResponseDto> result = invoiceService.getAllInvoices();

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).amount());
        assertEquals("PAID", result.get(0).status());
    }
}