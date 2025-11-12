package pe.edu.upc.center.workstation.paymentsManagement.application.internal.queryservices;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        return paymentRepository.findById(query.paymentId());
    }

    @Override
    public List<Payment> handle(GetPaymentsByInvoiceIdQuery query) {
        return paymentRepository.findByInvoiceId(query.invoiceId());
    }
}