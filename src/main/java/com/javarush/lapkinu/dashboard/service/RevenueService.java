package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.dto.RevenueDto;
import com.javarush.lapkinu.dashboard.repository.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;

    public List<RevenueDto> getAllRevenue() {
        return revenueRepository.findAll()
                .stream()
                .map(revenue -> new RevenueDto(revenue.getMonth(), revenue.getRevenue()))
                .toList();
    }
}