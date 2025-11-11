package pe.edu.upc.center.workstation.bookingManagement.domain.model.commands;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;

import java.util.Objects;

public record DeleteBookingCommand(BookingCode bookingCode) {

    public DeleteBookingCommand{
        Objects.requireNonNull(bookingCode, "The BookingCode can't be null");
    }
}
