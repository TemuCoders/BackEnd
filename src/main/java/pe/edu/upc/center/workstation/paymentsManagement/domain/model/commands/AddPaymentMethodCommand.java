package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

/**
 * Command: intención de agregar un método de pago.
 */
public record AddPaymentMethodCommand(
        Long userId,
        String type,
        String maskedPan,
        Integer expMonth,
        Integer expYear
) {
    public AddPaymentMethodCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Payment method type is required");
        }
    }
}