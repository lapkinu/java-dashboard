package com.javarush.lapkinu.dashboard.repository;

import com.javarush.lapkinu.dashboard.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {
    @Query("SELECT i FROM Invoice i JOIN FETCH i.customer ORDER BY i.date DESC LIMIT 10")
    List<Invoice> findTop5ByOrderByDateDesc();
}