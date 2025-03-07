package example.neontest_db.controller;

import example.neontest_db.dto.CardDataDto;
import example.neontest_db.service.CardDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/card-data")
@RequiredArgsConstructor
public class CardDataController {

    private final CardDataService cardDataService;

    @GetMapping
    public ResponseEntity<CardDataDto> getCardData() {
        return ResponseEntity.ok(cardDataService.getCardData());
    }
}