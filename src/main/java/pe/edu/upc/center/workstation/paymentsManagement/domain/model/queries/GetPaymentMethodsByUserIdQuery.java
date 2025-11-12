package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetPaymentMethodsByUserIdQuery(Long userId) {
    public GetPaymentMethodsByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
    }
}