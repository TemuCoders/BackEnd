package pe.edu.upc.center.workstation.bookingManagement.domain.model.commands;

import org.springframework.cglib.core.Local;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.SpaceId;

import java.time.LocalDate;
import java.util.Objects;

public record CreateBookingCommand(FreelancerId freelancerId,
                                   SpaceId spaceId,
                                   LocalDate bookingDate,
                                   LocalDate startDate,
                                   LocalDate endDate) {

    public CreateBookingCommand{
        Objects.requireNonNull(freelancerId, "The Freelancer Id can't be null");
        Objects.requireNonNull(spaceId, "The Space Id can't be null");
        Objects.requireNonNull(bookingDate, "The Booking Date can't be null");
        Objects.requireNonNull(startDate, "The Start Date can't be null");
        Objects.requireNonNull(endDate, "The End Date can't be null");
    }
}
