package pe.edu.upc.center.workstation.paymentsManagement.domain.services.domainservices;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Domain Service: procesador de pagos DEMO (sin integración real).
 */
@Service
public class PaymentProcessorDemoService {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessorDemoService.class);

    /**
     * Procesa un pago (DEMO: siempre exitoso).
     */
    public void processPayment(Payment payment) {
        log.info("DEMO: Processing payment {} for amount {}",
                payment.getId(), payment.getAmount());

        // Simular procesamiento exitoso
        payment.markAsPaid();

        log.info("DEMO: Payment {} processed successfully", payment.getId());
    }

    /**
     * Procesa un reembolso (DEMO: siempre exitoso).
     */
    public void processRefund(Payment payment) {
        log.info("DEMO: Processing refund for payment {} of amount {}",
                payment.getId(), payment.getAmount());

        // Simular reembolso exitoso
        payment.refund();

        log.info("DEMO: Refund for payment {} processed successfully", payment.getId());
    }
}