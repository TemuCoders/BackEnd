package pe.edu.upc.center.workstation.paymentsManagement.application.internal.queryservices;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentMethodQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaymentMethodQueryServiceImpl implements PaymentMethodQueryService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodQueryServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Optional<PaymentMethod> handle(GetPaymentMethodByIdQuery query) {
        return paymentMethodRepository.findById(query.paymentMethodId());
    }

    @Override
    public List<PaymentMethod> handle(GetPaymentMethodsByUserIdQuery query) {
        return paymentMethodRepository.findByUserId(query.userId());
    }
}