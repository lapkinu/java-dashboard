package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.InvoicesTableDto;
import com.javarush.lapkinu.dashboard.entity.Invoice;
import com.javarush.lapkinu.dashboard.repository.InvoiceRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoicesTableService {

    private final InvoiceRepository invoiceRepository;
    private static final int ITEMS_PER_PAGE = 10;

    @Transactional(readOnly = true)
    public List<InvoicesTableDto> getFilteredInvoices(String query, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, ITEMS_PER_PAGE);
        Page<Invoice> invoicesPage = invoiceRepository.findAll(getSpecification(query), pageable);

        return invoicesPage.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Specification<Invoice> getSpecification(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isEmpty()) {
                try {
                    // Попытка преобразовать query в число для фильтрации по amount
                    int amountQuery = Integer.parseInt(query);
                    predicates.add(criteriaBuilder.equal(root.get("amount"), amountQuery * 100)); // Умножаем на 100, так как amount хранится в центах
                } catch (NumberFormatException e) {
                    // Если query не является числом, продолжаем фильтрацию по остальным полям
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("name")), "%" + query.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("email")), "%" + query.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("to_char", String.class, root.get("date"), criteriaBuilder.literal("YYYY-MM-DD"))), "%" + query.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), "%" + query.toLowerCase() + "%")
                    ));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private InvoicesTableDto toDto(Invoice invoice) {
        return new InvoicesTableDto(
                invoice.getId(),
                invoice.getCustomer().getId(),
                invoice.getCustomer().getName(),
                invoice.getCustomer().getEmail(),
                invoice.getCustomer().getImageUrl(),
                invoice.getDate(),
                invoice.getAmount() / 100.0,
                invoice.getStatus().name()
        );
    }

    @Transactional(readOnly = true)
    public long getTotalPages(String query) {
        long totalItems = invoiceRepository.count(getSpecification(query));
        return (long) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    }
}