package example.neontest_db.controller;

import example.neontest_db.dto.RevenueDto;
import example.neontest_db.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/revenue")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping
    public ResponseEntity<List<RevenueDto>> getAllRevenue() {
        return ResponseEntity.ok(revenueService.getAllRevenue());
    }
}