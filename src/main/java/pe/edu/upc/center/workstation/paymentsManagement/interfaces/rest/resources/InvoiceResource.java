package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Schema(name = "InvoiceResponse", description = "Invoice information")
public record InvoiceResource(
        @Schema(description = "Invoice ID")
        Long id,

        @Schema(description = "Invoice number")
        String invoiceNumber,

        @Schema(description = "Booking ID")
        Long bookingId,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "Currency")
        String currency,

        @Schema(description = "Subtotal")
        BigDecimal subtotal,

        @Schema(description = "Tax")
        BigDecimal tax,

        @Schema(description = "Total")
        BigDecimal total,

        @Schema(description = "Status")
        String status,

        @Schema(description = "Issued at")
        Date issuedAt,

        @Schema(description = "Sent at")
        Date sentAt,

        @Schema(description = "Invoice lines")
        List<InvoiceLineDetailResource> lines,

        @Schema(description = "Created at")
        Date createdAt,

        @Schema(description = "Updated at")
        Date updatedAt
) {

    @Schema(name = "InvoiceLineDetail")
    public record InvoiceLineDetailResource(
            Long id,
            String description,
            int quantity,
            BigDecimal unitAmount,
            BigDecimal tax,
            BigDecimal subtotal,
            BigDecimal total
    ) {}
}