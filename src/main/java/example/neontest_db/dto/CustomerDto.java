package example.neontest_db.dto;

import example.neontest_db.entity.Customer;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String email,
        String imageUrl
) {
    public static CustomerDto fromEntity(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getImageUrl()
        );
    }
}