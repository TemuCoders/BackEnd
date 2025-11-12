package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreatePaymentRequest", description = "Request to create a new payment")
public record CreatePaymentResource(

        @Schema(description = "Invoice ID", example = "1")
        @NotNull(message = "Invoice ID is required")
        Long invoiceId,

        @Schema(description = "Booking ID", example = "1")
        @NotNull(message = "Booking ID is required")
        Long bookingId,

        @Schema(description = "User ID", example = "1")
        @NotNull(message = "User ID is required")
        Long userId,

        @Schema(description = "Payment method ID", example = "1")
        @NotNull(message = "Payment method ID is required")
        Long paymentMethodId
) {
}