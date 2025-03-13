package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomerFieldDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerFieldService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerFieldDto> getCustomersForForm() {
        return customerRepository.findAllByOrderByNameAsc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CustomerFieldDto toDto(Customer customer) {
        return new CustomerFieldDto(
                customer.getId(),
                customer.getName()
        );
    }
}