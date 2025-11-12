package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentMethodResource;

public class PaymentMethodResourceFromEntityAssembler {

    public static PaymentMethodResource toResourceFromEntity(PaymentMethod entity) {
        return new PaymentMethodResource(
                entity.getId(),
                entity.getUserId(),
                entity.getType().name(),
                entity.getMaskedPan(),
                entity.getExpMonth(),
                entity.getExpYear(),
                entity.getStatus().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}