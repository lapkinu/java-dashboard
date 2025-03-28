package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomersTableTypeDto;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilteredCustomersServiceTest {

    private FilteredCustomersService filteredCustomersService;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        filteredCustomersService = new FilteredCustomersService(customerRepository);
    }

    @Test
    void getFilteredCustomers_noQuery_returnsAll() {
        CustomersTableTypeDto dto = new CustomersTableTypeDto(
                UUID.randomUUID(), "Test Customer", "test@example.com", "test.jpg", 2L, 100.0, 50.0
        );
        when(customerRepository.findFilteredCustomers(null)).thenReturn(List.of(dto));

        List<CustomersTableTypeDto> result = filteredCustomersService.getFilteredCustomers(null);

        assertEquals(1, result.size());
        assertEquals("Test Customer", result.get(0).name());
    }

    @Test
    void getFilteredCustomers_withQuery_filtersCorrectly() {
        CustomersTableTypeDto dto = new CustomersTableTypeDto(
                UUID.randomUUID(), "John Doe", "john@example.com", "john.jpg", 1L, 0.0, 200.0
        );
        when(customerRepository.findFilteredCustomers("john")).thenReturn(List.of(dto));

        List<CustomersTableTypeDto> result = filteredCustomersService.getFilteredCustomers("john");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).name());
    }

    @Test
    void getFilteredCustomers_emptyResult_returnsEmptyList() {
        when(customerRepository.findFilteredCustomers("nonexistent")).thenReturn(Collections.emptyList());

        List<CustomersTableTypeDto> result = filteredCustomersService.getFilteredCustomers("nonexistent");

        assertTrue(result.isEmpty());
    }
}