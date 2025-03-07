package example.neontest_db.repository;

import example.neontest_db.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<Revenue, String> {
}