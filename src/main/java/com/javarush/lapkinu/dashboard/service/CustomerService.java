package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomerDto;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }
}