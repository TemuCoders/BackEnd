package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangeStatusBookingRequest(
        @JsonProperty("bookingCode")
        @NotNull @Positive
        Long bookingCode,
        @JsonProperty("bookingStatus")
        @NotNull
        String bookingStatus
) {
}
