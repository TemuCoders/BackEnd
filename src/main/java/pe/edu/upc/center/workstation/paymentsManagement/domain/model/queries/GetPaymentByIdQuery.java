package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetPaymentByIdQuery(Long paymentId) {
    public GetPaymentByIdQuery {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("PaymentId is required and must be positive");
        }
    }
}