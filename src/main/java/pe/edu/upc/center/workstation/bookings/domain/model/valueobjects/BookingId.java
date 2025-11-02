package pe.edu.upc.center.workstation.bookings.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record BookingId(Long value) {
    public BookingId {
        if (value == null || value <= 0) throw new IllegalArgumentException("BookingId must be positive");
    }
}
