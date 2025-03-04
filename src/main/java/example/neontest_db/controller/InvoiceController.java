package example.neontest_db.controller;

import example.neontest_db.dto.CreateInvoiceDto;
import example.neontest_db.dto.InvoiceResponseDto;
import example.neontest_db.dto.UpdateInvoiceDto;
import example.neontest_db.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @Valid @RequestBody CreateInvoiceDto dto
    ) {
        InvoiceResponseDto createdInvoice = invoiceService.createInvoice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInvoice(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateInvoiceDto dto
    ) {
        invoiceService.updateInvoice(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }
}