package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateBookingRequest(
        @JsonProperty("freelancerId")
        @NotNull @Positive
        Long freelancerId,
        @JsonProperty("spaceId")
        @NotNull @Positive
        Long spaceId,
        @JsonProperty("bookingDate")
        @NotNull
        LocalDate bookingDate,
        @JsonProperty("startDate")
        @NotNull
        LocalDate startDate,
        @JsonProperty("endDate")
        @NotNull
        LocalDate endDate
) {
}
