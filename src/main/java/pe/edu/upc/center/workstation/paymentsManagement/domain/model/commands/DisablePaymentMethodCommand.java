package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

/**
 * Command: intención de deshabilitar un método de pago.
 */
public record DisablePaymentMethodCommand(Long paymentMethodId) {
    public DisablePaymentMethodCommand {
        if (paymentMethodId == null || paymentMethodId <= 0) {
            throw new IllegalArgumentException("PaymentMethodId is required and must be positive");
        }
    }
}