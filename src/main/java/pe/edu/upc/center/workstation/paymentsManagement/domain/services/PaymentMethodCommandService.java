package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;

public interface PaymentMethodCommandService {
    Long handle(AddPaymentMethodCommand command);
    void handle(DisablePaymentMethodCommand command);
}