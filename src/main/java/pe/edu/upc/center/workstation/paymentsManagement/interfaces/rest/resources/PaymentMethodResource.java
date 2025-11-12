package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "PaymentMethodResponse", description = "Payment method information")
public record PaymentMethodResource(
        @Schema(description = "Payment method ID")
        Long id,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "Type")
        String type,

        @Schema(description = "Masked PAN")
        String maskedPan,

        @Schema(description = "Expiration month")
        Integer expMonth,

        @Schema(description = "Expiration year")
        Integer expYear,

        @Schema(description = "Status")
        String status,

        @Schema(description = "Created at")
        Date createdAt,

        @Schema(description = "Updated at")
        Date updatedAt
) {
}