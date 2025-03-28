package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CreateInvoiceDto;
import com.javarush.lapkinu.dashboard.dto.InvoiceResponseDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.entity.Invoice;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import com.javarush.lapkinu.dashboard.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
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

    // Негативный сценарий: клиент не найден
    @Test
    void createInvoice_customerNotFound_throwsException() {
        UUID customerId = UUID.randomUUID();
        CreateInvoiceDto dto = new CreateInvoiceDto(customerId, 100.0, "PENDING");
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> invoiceService.createInvoice(dto));
        verifyNoInteractions(invoiceRepository);
    }

    // Негативный сценарий: некорректный статус
    @Test
    void createInvoice_invalidStatus_throwsException() {
        UUID customerId = UUID.randomUUID();
        CreateInvoiceDto dto = new CreateInvoiceDto(customerId, 100.0, "INVALID");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));

        assertThrows(IllegalArgumentException.class, () -> invoiceService.createInvoice(dto));
    }

    // Граничное значение: максимальная сумма
    @Test
    void createInvoice_maxAmount_success() {
        UUID customerId = UUID.randomUUID();
        double maxAmount = Integer.MAX_VALUE / 100.0; // Максимальная сумма, учитывая int
        CreateInvoiceDto dto = new CreateInvoiceDto(customerId, maxAmount, "PENDING");
        Customer customer = new Customer();
        Invoice savedInvoice = new Invoice();
        savedInvoice.setId(UUID.randomUUID());
        savedInvoice.setCustomer(customer);
        savedInvoice.setAmount((int) (maxAmount * 100)); // 2,147,483,600
        savedInvoice.setStatus(com.javarush.lapkinu.dashboard.entity.InvoiceStatus.PENDING);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);

        InvoiceResponseDto result = invoiceService.createInvoice(dto);

        assertEquals(maxAmount, result.amount(), 0.01); // Ожидаем 21,474,836.47
    }

    // Граничное значение: пустой список инвойсов
    @Test
    void getAllInvoices_emptyList_returnsEmpty() {
        when(invoiceRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(invoiceService.getAllInvoices().isEmpty());
    }
}