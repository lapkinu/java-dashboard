package example.neontest_db.controller;

import example.neontest_db.dto.CreateInvoiceDto;
import example.neontest_db.dto.InvoiceResponseDto;
import example.neontest_db.dto.InvoicesTableDto;
import example.neontest_db.dto.UpdateInvoiceDto;
import example.neontest_db.service.InvoiceService;
import example.neontest_db.service.InvoicesTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoicesTableService invoicesTableService;

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

    @GetMapping("/filtered")
    public ResponseEntity<List<InvoicesTableDto>> getFilteredInvoices(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
    ) {
        List<InvoicesTableDto> invoices = invoicesTableService.getFilteredInvoices(query, currentPage);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/pages")
    public ResponseEntity<Long> getInvoicesPages(@RequestParam(value = "query", required = false) String query) {
        long totalPages = invoicesTableService.getTotalPages(query);
        return ResponseEntity.ok(totalPages);
    }
}