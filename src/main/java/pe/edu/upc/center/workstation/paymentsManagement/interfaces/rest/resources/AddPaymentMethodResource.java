package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "AddPaymentMethodRequest", description = "Request to add a payment method")
public record AddPaymentMethodResource(

        @Schema(description = "User ID", example = "1")
        @NotNull(message = "User ID is required")
        Long userId,

        @Schema(description = "Payment method type", example = "CARD", allowableValues = {"CARD", "CASH_DEMO", "TRANSFER_DEMO"})
        @NotBlank(message = "Type is required")
        String type,

        @Schema(description = "Masked PAN (for CARD)", example = "****1234")
        String maskedPan,

        @Schema(description = "Expiration month (for CARD)", example = "12")
        Integer expMonth,

        @Schema(description = "Expiration year (for CARD)", example = "2028")
        Integer expYear
) {
}