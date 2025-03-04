package example.neontest_db.util;

import example.neontest_db.entity.InvoiceStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InvoiceStatusConverter implements AttributeConverter<InvoiceStatus, String> {
    @Override
    public String convertToDatabaseColumn(InvoiceStatus status) {
        return status.name().toLowerCase();
    }

    @Override
    public InvoiceStatus convertToEntityAttribute(String dbData) {
        return InvoiceStatus.valueOf(dbData.toUpperCase());
    }
}
