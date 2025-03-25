package com.javarush.lapkinu.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "revenue")
public class Revenue {

    @Id
    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "revenue", nullable = false)
    private int revenue;
}