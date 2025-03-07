package example.neontest_db.dto;

import java.util.UUID;

public record CustomerFieldDto(
        UUID id,
        String name
) {
}