package example.neontest_db.controller;

import example.neontest_db.dto.CustomersTableTypeDto;
import example.neontest_db.service.FilteredCustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filtered-customers")
@RequiredArgsConstructor
public class FilteredCustomersController {

    private final FilteredCustomersService filteredCustomersService;

    @GetMapping
    public ResponseEntity<List<CustomersTableTypeDto>> getFilteredCustomers(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(filteredCustomersService.getFilteredCustomers(query));
    }
}