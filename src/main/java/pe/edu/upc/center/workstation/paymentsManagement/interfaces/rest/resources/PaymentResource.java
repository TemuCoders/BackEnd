package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

@Schema(name = "PaymentResponse", description = "Payment information")
public record PaymentResource(
        @Schema(description = "Payment ID")
        Long id,

        @Schema(description = "Invoice ID")
        Long invoiceId,

        @Schema(description = "Booking ID")
        Long bookingId,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "Amount")
        BigDecimal amount,

        @Schema(description = "Currency")
        String currency,

        @Schema(description = "Status")
        String status,

        @Schema(description = "Payment method ID")
        Long paymentMethodId,

        @Schema(description = "Paid at")
        Date paidAt,

        @Schema(description = "Refunded at")
        Date refundedAt,

        @Schema(description = "Created at")
        Date createdAt,

        @Schema(description = "Updated at")
        Date updatedAt
) {
}