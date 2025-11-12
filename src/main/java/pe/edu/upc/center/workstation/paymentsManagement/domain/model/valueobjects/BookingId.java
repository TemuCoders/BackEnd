package pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects;

/**
 * Value Object: referencia al Bounded Context de Booking.
 */
public record BookingId(Long bookingId) {

    public BookingId {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("BookingId must be positive");
        }
    }

    public BookingId() {
        this(0L);
    }
}