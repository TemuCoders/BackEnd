package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "InvoiceLineRequest")
public record InvoiceLineResource(

        @Schema(description = "Line description", example = "Space rental")
        @NotBlank(message = "Description is required")
        String description,

        @Schema(description = "Quantity", example = "1")
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @Schema(description = "Unit amount", example = "100.00")
        @NotNull(message = "Unit amount is required")
        double unitAmount,

        @Schema(description = "Tax amount", example = "18.00")
        @NotNull(message = "Tax is required")
        double tax
) {
}