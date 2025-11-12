package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetPaymentMethodByIdQuery(Long paymentMethodId) {
    public GetPaymentMethodByIdQuery {
        if (paymentMethodId == null || paymentMethodId <= 0) {
            throw new IllegalArgumentException("PaymentMethodId is required and must be positive");
        }
    }
}