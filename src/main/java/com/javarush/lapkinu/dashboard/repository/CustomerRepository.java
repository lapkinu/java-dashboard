package com.javarush.lapkinu.dashboard.repository;

import com.javarush.lapkinu.dashboard.dto.CustomersTableTypeDto;
import com.javarush.lapkinu.dashboard.entity.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getObject("id", UUID.class)); // Получаем UUID напрямую
        customer.setName(rs.getString("name"));         // Оставляем как есть, так как NOT NULL
        customer.setEmail(rs.getString("email"));       // Оставляем как есть, так как NOT NULL
        customer.setImageUrl(rs.getString("image_url")); // Оставляем как есть, так как NOT NULL
        return customer;
    };

    // Маппер для CustomersTableTypeDto
    private static final RowMapper<CustomersTableTypeDto> CUSTOMER_TABLE_DTO_MAPPER = (rs, rowNum) -> new CustomersTableTypeDto(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("image_url"),
            rs.getLong("total_invoices"),
            rs.getDouble("total_pending"),
            rs.getDouble("total_paid")
    );

    // Найти всех клиентов
    public List<Customer> findAll() {
        String sql = "SELECT id, name, email, image_url FROM customers";
        return jdbcTemplate.query(sql, CUSTOMER_ROW_MAPPER);
    }

    // Найти клиента по ID
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT id, name, email, image_url FROM customers WHERE id = ?";
        List<Customer> customers = jdbcTemplate.query(sql, CUSTOMER_ROW_MAPPER, id); // Передаем UUID напрямую
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    // Найти всех клиентов, отсортированных по имени
    public List<Customer> findAllByOrderByNameAsc() {
        String sql = "SELECT id, name, email, image_url FROM customers ORDER BY name ASC";
        return jdbcTemplate.query(sql, CUSTOMER_ROW_MAPPER);
    }

    public Customer save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getName() == null || customer.getEmail() == null || customer.getImageUrl() == null) {
            throw new IllegalArgumentException("Customer fields (name, email, imageUrl) cannot be null");
        }

        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
            String sql = "INSERT INTO customers (id, name, email, image_url) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getImageUrl());
        } else {
            String sql = "UPDATE customers SET name = ?, email = ?, image_url = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql,
                    customer.getName(),
                    customer.getEmail(),
                    customer.getImageUrl(),
                    customer.getId());
            if (rowsAffected == 0) {
                throw new IllegalStateException("Customer with ID " + customer.getId() + " not found for update");
            }
        }
        return customer;
    }

    // Удалить клиента по ID
    public void deleteById(UUID id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        jdbcTemplate.update(sql, id); // Передаем UUID
    }

    // Найти отфильтрованных клиентов с данными об инвойсах
    public List<CustomersTableTypeDto> findFilteredCustomers(String query) {
        String sql = """
            SELECT 
                c.id, 
                c.name, 
                c.email, 
                c.image_url,
                COUNT(i.id) AS total_invoices,
                COALESCE(SUM(CASE WHEN i.status = 'PENDING' THEN i.amount / 100.0 ELSE 0 END), 0) AS total_pending,
                COALESCE(SUM(CASE WHEN i.status = 'PAID' THEN i.amount / 100.0 ELSE 0 END), 0) AS total_paid
            FROM customers c
            LEFT JOIN invoices i ON c.id = i.customer_id
            WHERE ? IS NULL OR LOWER(c.name) LIKE LOWER(?) OR LOWER(c.email) LIKE LOWER(?)
            GROUP BY c.id, c.name, c.email, c.image_url
            """;

        String queryPattern = "%" + (query != null ? query : "") + "%";
        return jdbcTemplate.query(sql, CUSTOMER_TABLE_DTO_MAPPER, query, queryPattern, queryPattern);
    }
}