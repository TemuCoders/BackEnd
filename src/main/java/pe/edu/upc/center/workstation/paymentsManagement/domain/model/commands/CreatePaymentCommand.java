package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

/**
 * Command: intención de crear un pago.
 */
public record CreatePaymentCommand(
        Long invoiceId,
        Long bookingId,
        Long userId,
        Long paymentMethodId
) {
    public CreatePaymentCommand {
        if (invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("InvoiceId is required and must be positive");
        }
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("BookingId is required and must be positive");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
        if (paymentMethodId == null || paymentMethodId <= 0) {
            throw new IllegalArgumentException("PaymentMethodId is required and must be positive");
        }
    }
}