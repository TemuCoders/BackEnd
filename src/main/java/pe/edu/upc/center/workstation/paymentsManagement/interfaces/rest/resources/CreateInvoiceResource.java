package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "CreateInvoiceRequest", description = "Request to create a new invoice")
public record CreateInvoiceResource(

        @Schema(description = "Booking ID", example = "1")
        @NotNull(message = "Booking ID is required")
        Long bookingId,

        @Schema(description = "User ID", example = "1")
        @NotNull(message = "User ID is required")
        Long userId,

        @Schema(description = "Currency code", example = "USD")
        @NotBlank(message = "Currency is required")
        String currency,

        @Schema(description = "Invoice lines")
        @NotEmpty(message = "Invoice must have at least one line")
        @Valid
        List<InvoiceLineResource> lines
) {
}