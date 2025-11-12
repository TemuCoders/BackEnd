package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;

import java.util.Optional;

public interface PaymentCommandService {
    Long handle(CreatePaymentCommand command);
    Optional<Payment> handle(RefundPaymentCommand command);
}