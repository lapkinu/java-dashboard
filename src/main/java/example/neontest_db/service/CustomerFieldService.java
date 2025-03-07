package example.neontest_db.service;

import example.neontest_db.dto.CustomerFieldDto;
import example.neontest_db.entity.Customer;
import example.neontest_db.repository.CustomerRepository;
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