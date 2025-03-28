package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomerDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getAllCustomers_success() {
        Customer customer = new Customer();
        UUID id = UUID.randomUUID();
        customer.setId(id);
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setImageUrl("test.jpg");

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerDto> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertEquals("Test Customer", result.get(0).name());
        assertEquals("test@example.com", result.get(0).email());
        assertEquals("test.jpg", result.get(0).imageUrl());
    }
}