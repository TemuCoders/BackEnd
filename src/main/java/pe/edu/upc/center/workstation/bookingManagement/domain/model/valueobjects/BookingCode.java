package pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects;


import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique code for a booking.
 *
 * @param code the booking code, it must not be null or blank and must be a valid UUID
 *
 * @author Open Source Application Development Team
 */
@Embeddable
public record BookingCode(String code) {
    public BookingCode() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Constructs a BookingCode object with validation.
     *
     * @param code the booking code, it must not be null or blank and must be a valid UUID
     */
    public BookingCode {
        if (Objects.isNull(code) || code.isBlank()) {
            throw new IllegalArgumentException("Booking code cannot be null or blank");
        }
        if (code.length() != 36) {
            throw new IllegalArgumentException("Booking code must be 36 characters long");
        }
        if (!code.matches("[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}")) {
            throw new IllegalArgumentException("Student code must be a valid UUID");
        }
    }
}
