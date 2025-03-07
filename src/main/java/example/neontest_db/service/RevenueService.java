package example.neontest_db.service;

import example.neontest_db.dto.RevenueDto;
import example.neontest_db.repository.RevenueRepository;
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