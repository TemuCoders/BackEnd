package pe.edu.upc.center.workstation.bookingManagement.domain.services;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.ChangeStatusBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.CreateBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.DeleteBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetAllBookingsQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByCodeQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByFreelancerIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;

import java.util.List;
import java.util.Optional;

public interface BookingCommandService {

    BookingCode handle(CreateBookingCommand command);

    Optional<Booking> handle(ChangeStatusBookingCommand command);

    void handle(DeleteBookingCommand command);

}
