package pe.edu.upc.center.workstation.paymentsManagement.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Qualifier;
import pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl.ExternalUserService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions.InvalidPaymentMethodException;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentMethodCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.PaymentMethodRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentMethodCommandServiceImpl implements PaymentMethodCommandService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ExternalUserService externalUserService;

    public PaymentMethodCommandServiceImpl(
            PaymentMethodRepository paymentMethodRepository,
            @Qualifier("paymentsExternalUserService") ExternalUserService externalUserService
    ) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.externalUserService = externalUserService;
    }

    @Override
    @Transactional
    public Long handle(AddPaymentMethodCommand command) {
        // 1. Validar User existe
        externalUserService.validateUserExists(command.userId());

        // 2. Parsear tipo de método de pago
        PaymentMethodType type;
        try {
            type = PaymentMethodType.valueOf(command.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPaymentMethodException(
                    "Invalid payment method type: " + command.type()
            );
        }

        // 3. Crear agregado PaymentMethod
        var paymentMethod = new PaymentMethod(
                new UserId(command.userId()),
                type,
                command.maskedPan(),
                command.expMonth(),
                command.expYear()
        );

        // 4. Persistir
        try {
            paymentMethodRepository.save(paymentMethod);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving payment method: " + e.getMessage());
        }

        return paymentMethod.getId();
    }

    @Override
    @Transactional
    public void handle(DisablePaymentMethodCommand command) {
        // 1. Buscar método de pago
        var paymentMethod = paymentMethodRepository.findById(command.paymentMethodId())
                .orElseThrow(() -> new NotFoundIdException(PaymentMethod.class, command.paymentMethodId()));

        // 2. Deshabilitar (método de dominio)
        paymentMethod.disable();

        // 3. Persistir
        try {
            paymentMethodRepository.save(paymentMethod);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error disabling payment method: " + e.getMessage());
        }
    }
}