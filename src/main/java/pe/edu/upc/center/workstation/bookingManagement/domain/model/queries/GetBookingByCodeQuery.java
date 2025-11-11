package pe.edu.upc.center.workstation.bookingManagement.domain.model.queries;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;

import java.util.Objects;

public record GetBookingByCodeQuery(BookingCode code) {

    public GetBookingByCodeQuery {
        Objects.requireNonNull(code, "The Booking Code can't be null");
    }
}
