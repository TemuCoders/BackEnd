package pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BookingStatus(String status) {

    public BookingStatus {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Booking status cannot be null or blank");
        }

        if (!status.equalsIgnoreCase("PENDING") &&
                !status.equalsIgnoreCase("CONFIRMED") &&
                !status.equalsIgnoreCase("CANCELLED")) {
            throw new IllegalArgumentException("Invalid booking status: " + status);
        }
    }

    public static BookingStatus pending() { return new BookingStatus("PENDING"); }
    public static BookingStatus confirmed() { return new BookingStatus("CONFIRMED"); }
    public static BookingStatus cancelled() { return new BookingStatus("CANCELLED"); }
}