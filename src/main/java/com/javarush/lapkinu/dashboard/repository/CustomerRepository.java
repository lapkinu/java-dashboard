package com.javarush.lapkinu.dashboard.repository;

import com.javarush.lapkinu.dashboard.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    List<Customer> findAllByOrderByNameAsc();
}
