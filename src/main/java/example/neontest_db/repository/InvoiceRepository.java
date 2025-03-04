package example.neontest_db.repository;

import example.neontest_db.entity.Invoice;
import example.neontest_db.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
