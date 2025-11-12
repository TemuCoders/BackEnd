package pe.edu.upc.center.workstation.paymentsManagement.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Qualifier;
import pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl.ExternalBookingService;
import pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl.ExternalUserService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.domainservices.PaymentProcessorDemoService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.PaymentMethodRepository;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.PaymentRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ExternalUserService externalUserService;
    private final ExternalBookingService externalBookingService;
    private final PaymentProcessorDemoService paymentProcessorDemoService;

    public PaymentCommandServiceImpl(
            PaymentRepository paymentRepository,
            InvoiceRepository invoiceRepository,
            PaymentMethodRepository paymentMethodRepository,
            @Qualifier("paymentsExternalUserService") ExternalUserService externalUserService,
            ExternalBookingService externalBookingService,
            PaymentProcessorDemoService paymentProcessorDemoService
    ) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.externalUserService = externalUserService;
        this.externalBookingService = externalBookingService;
        this.paymentProcessorDemoService = paymentProcessorDemoService;
    }

    @Override
    @Transactional
    public Long handle(CreatePaymentCommand command) {
        // 1. Validar User existe
        externalUserService.validateUserExists(command.userId());

        // 2. Validar Booking existe
        externalBookingService.validateBookingExists(command.bookingId());

        // 3. Buscar factura
        Invoice invoice = invoiceRepository.findById(command.invoiceId())
                .orElseThrow(() -> new NotFoundIdException(Invoice.class, command.invoiceId()));

        // 4. Validar que la factura no esté ya pagada
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid: " + command.invoiceId());
        }

        // 5. Buscar método de pago
        PaymentMethod paymentMethod = paymentMethodRepository.findById(command.paymentMethodId())
                .orElseThrow(() -> new NotFoundIdException(PaymentMethod.class, command.paymentMethodId()));

        // 6. Validar que el método de pago esté activo
        if (!paymentMethod.isActive()) {
            throw new IllegalStateException(
                    "Payment method is not active: " + command.paymentMethodId()
            );
        }

        // 7. Validar que el método de pago pertenezca al usuario
        if (!paymentMethod.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException(
                    "Payment method does not belong to user: " + command.userId()
            );
        }

        // 8. Crear agregado Payment
        var payment = new Payment(
                invoice.getId(),
                new BookingId(command.bookingId()),
                new UserId(command.userId()),
                invoice.getTotal(),
                paymentMethod.getId()
        );

        // 9. Procesar pago (Domain Service - DEMO)
        paymentProcessorDemoService.processPayment(payment);

        // 10. Marcar factura como pagada
        invoice.markAsPaid();
        invoiceRepository.save(invoice);

        // 11. Persistir pago
        try {
            paymentRepository.save(payment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving payment: " + e.getMessage());
        }

        return payment.getId();
    }

    @Override
    @Transactional
    public Optional<Payment> handle(RefundPaymentCommand command) {
        // 1. Buscar pago
        var payment = paymentRepository.findById(command.paymentId())
                .orElseThrow(() -> new NotFoundIdException(Payment.class, command.paymentId()));

        // 2. Procesar reembolso (Domain Service - DEMO)
        paymentProcessorDemoService.processRefund(payment);

        // 3. Persistir
        try {
            var updated = paymentRepository.save(payment);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error refunding payment: " + e.getMessage());
        }
    }
}