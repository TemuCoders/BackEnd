package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {

    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getBookingId(),
                entity.getUserId(),
                entity.getAmount().amount(),
                entity.getAmount().currency(),
                entity.getStatus().name(),
                entity.getPaymentMethodId(),
                entity.getPaidAt(),
                entity.getRefundedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}