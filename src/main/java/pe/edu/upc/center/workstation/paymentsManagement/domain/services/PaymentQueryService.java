package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(GetPaymentByIdQuery query);
    List<Payment> handle(GetPaymentsByInvoiceIdQuery query);
}