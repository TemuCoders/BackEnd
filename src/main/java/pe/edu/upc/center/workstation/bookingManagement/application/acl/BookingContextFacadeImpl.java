package pe.edu.upc.center.workstation.bookingManagement.application.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.CreateBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByFreelancerIdQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingCommandService;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingQueryService;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.acl.BookingsContextFacade;

import java.time.LocalDate;

@Service
public class BookingContextFacadeImpl implements BookingsContextFacade {

    private final BookingCommandService bookingCommandService;
    private final BookingQueryService bookingQueryService;

    public BookingContextFacadeImpl(
            BookingCommandService bookingCommandService,
            BookingQueryService bookingQueryService) {
        this.bookingCommandService = bookingCommandService;
        this.bookingQueryService = bookingQueryService;
    }

    @Override
    public String createBooking(
            Long freelancerId,
            Long spaceId,
            String bookingDate,
            String startDate,
            String endDate
    ) {
        var createBookingCommand = new CreateBookingCommand(
                new FreelancerId(freelancerId),
                new SpaceId(spaceId),
                LocalDate.parse(bookingDate),
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );

        var bookingCode = bookingCommandService.handle(createBookingCommand);

        return bookingCode.code();
    }

    @Override
    public Long fetchBookingCode(Long freelancerId, Long spaceId) {

        var query = new GetBookingByFreelancerIdQuery(
                new FreelancerId(freelancerId)
        );

        var booking = bookingQueryService.handle(query);

        return booking.isPresent()
                ? booking.get().getId()
                : null;
    }
}
