package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomerDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void getAllCustomers_repositoryError_throwsException() {
        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> customerService.getAllCustomers());
    }

    @Test
    void getAllCustomers_emptyList_returnsEmpty() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDto> result = customerService.getAllCustomers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllCustomers_longName_success() {
        Customer customer = new Customer();
        UUID id = UUID.randomUUID();
        String longName = "a".repeat(255);
        customer.setId(id);
        customer.setName(longName);
        customer.setEmail("test@example.com");
        customer.setImageUrl("test.jpg");
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        List<CustomerDto> result = customerService.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals(longName, result.get(0).name());
    }
}