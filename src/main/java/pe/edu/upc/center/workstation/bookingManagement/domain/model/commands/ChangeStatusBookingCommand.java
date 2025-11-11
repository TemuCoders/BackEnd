package pe.edu.upc.center.workstation.bookingManagement.domain.model.commands;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingStatus;

import java.util.Objects;


public record ChangeStatusBookingCommand(BookingCode bookingCode, BookingStatus bookingStatus) {
    public ChangeStatusBookingCommand {
        Objects.requireNonNull(bookingCode, "The Booking Code can't be null");
        Objects.requireNonNull(bookingStatus, "The Booking Status can't be null");
    }
}
