package com.javarush.lapkinu.dashboard.repository;

import com.javarush.lapkinu.dashboard.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<Revenue, String> {
}