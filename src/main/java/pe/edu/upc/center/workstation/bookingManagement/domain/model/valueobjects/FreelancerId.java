package pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record FreelancerId(Long freelancerId) {

    public FreelancerId {
        if (Objects.isNull(freelancerId) || freelancerId <= 0) {
            throw new IllegalArgumentException("Freelancer ID cannot be null or negative.");
        }
    }

    public FreelancerId() {
        this(0L);
    }
}
