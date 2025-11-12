package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

/**
 * Command: intención de reembolsar un pago.
 */
public record RefundPaymentCommand(Long paymentId) {
    public RefundPaymentCommand {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("PaymentId is required and must be positive");
        }
    }
}