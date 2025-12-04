package pe.edu.upc.center.workstation.bookingManagement.domain.services;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetAllBookingsQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByCodeQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByFreelancerIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByIdQuery;

import java.util.List;
import java.util.Optional;

public interface BookingQueryService {

    List<Booking> handle(GetAllBookingsQuery query);
    Optional<Booking> handle(GetBookingByIdQuery query);
    Optional<Booking> handle(GetBookingByCodeQuery query);
    Optional<Booking> handle(GetBookingByFreelancerIdQuery query);
}
