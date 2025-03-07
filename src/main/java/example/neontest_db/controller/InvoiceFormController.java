package example.neontest_db.controller;

import example.neontest_db.dto.InvoiceFormDto;
import example.neontest_db.service.InvoiceFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoice-form")
@RequiredArgsConstructor
public class InvoiceFormController {

    private final InvoiceFormService invoiceFormService;

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceFormDto> getInvoiceById(@PathVariable UUID id) {
        return ResponseEntity.ok(invoiceFormService.getInvoiceById(id));
    }
}