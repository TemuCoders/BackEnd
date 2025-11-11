package pe.edu.upc.center.workstation.bookingManagement.domain.model.queries;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;

import java.util.Objects;

public record GetBookingByFreelancerIdQuery(FreelancerId freelancerId) {

    public GetBookingByFreelancerIdQuery {
        Objects.requireNonNull(freelancerId, "The Freelancer Id can't be null");
    }
}
