package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

import java.util.List;

/**
 * Command: intención de crear una factura.
 */
public record CreateInvoiceCommand(
        Long bookingId,
        Long userId,
        String currency,
        List<InvoiceLineCommand> lines
) {
    public CreateInvoiceCommand {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("BookingId is required and must be positive");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Invoice must have at least one line");
        }
    }

    /**
     * Comando interno para líneas de factura.
     */
    public record InvoiceLineCommand(
            String description,
            int quantity,
            double unitAmount,
            double tax
    ) {
        public InvoiceLineCommand {
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("Line description is required");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            if (unitAmount < 0) {
                throw new IllegalArgumentException("Unit amount cannot be negative");
            }
            if (tax < 0) {
                throw new IllegalArgumentException("Tax cannot be negative");
            }
        }
    }
}