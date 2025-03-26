package com.javarush.lapkinu.dashboard.entity;

import com.javarush.lapkinu.dashboard.util.InvoiceStatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Convert(converter = InvoiceStatusConverter.class)
    @Enumerated(EnumType.STRING) // Можно удалить, если используете конвертер
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
}