package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodQueryService {
    Optional<PaymentMethod> handle(GetPaymentMethodByIdQuery query);
    List<PaymentMethod> handle(GetPaymentMethodsByUserIdQuery query);
}