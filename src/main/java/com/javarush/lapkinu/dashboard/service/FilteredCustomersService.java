package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.CustomersTableTypeDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import com.javarush.lapkinu.dashboard.entity.InvoiceStatus;
import com.javarush.lapkinu.dashboard.repository.CustomerRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilteredCustomersService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomersTableTypeDto> getFilteredCustomers(String query) {
        return customerRepository.findFilteredCustomers(query);
    }


    /*private Specification<Customer> getSpecification(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + query.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + query.toLowerCase() + "%")
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private CustomersTableTypeDto toDto(Customer customer) {
        long totalInvoices = customer.getInvoices().size();
        double totalPending = customer.getInvoices().stream()
                .filter(invoice -> invoice.getStatus() == InvoiceStatus.PENDING)
                .mapToDouble(invoice -> invoice.getAmount() / 100.0)
                .sum();
        double totalPaid = customer.getInvoices().stream()
                .filter(invoice -> invoice.getStatus() == InvoiceStatus.PAID)
                .mapToDouble(invoice -> invoice.getAmount() / 100.0)
                .sum();

        return new CustomersTableTypeDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getImageUrl(),
                totalInvoices,
                totalPending,
                totalPaid
        );
    }*/
}