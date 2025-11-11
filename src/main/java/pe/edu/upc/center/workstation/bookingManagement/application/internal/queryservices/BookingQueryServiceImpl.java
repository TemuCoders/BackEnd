package pe.edu.upc.center.workstation.bookingManagement.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetAllBookingsQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByCodeQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByFreelancerIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingQueryService;
import pe.edu.upc.center.workstation.bookingManagement.infrastructure.persistance.jpa.repositories.BookingRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

@Service
public class BookingQueryServiceImpl implements BookingQueryService {
    private final BookingRepository bookingRepository;

    public BookingQueryServiceImpl(BookingRepository bookingRepository) { this.bookingRepository = bookingRepository; }

    public List<Booking> handle(GetAllBookingsQuery query) {return this.bookingRepository.findAll();}

    public Optional<Booking> handle(GetBookingByIdQuery query) {
        return Optional.ofNullable(this.bookingRepository.findById(query.bookingId()))
                .orElseThrow(()-> new NotFoundIdException(Booking.class, query.bookingId()));
    }

    public Optional<Booking> handle(GetBookingByCodeQuery query) {
        return Optional.ofNullable(this.bookingRepository.findByCode(query.code())
                .orElseThrow(()-> new NotFoundArgumentException("Booking not found with booking ID: " + query.code())));
    }

    public Optional<Booking> handle(GetBookingByFreelancerIdQuery query){
        return Optional.ofNullable(this.bookingRepository.findByFreelancerId(query.freelancerId())
                .orElseThrow(()-> new NotFoundArgumentException("Booking not found with freelancerId: " + query.freelancerId())));
    }
}
