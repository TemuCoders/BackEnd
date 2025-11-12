package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.CreatePaymentResource;

public class CreatePaymentCommandFromResourceAssembler {

    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(
                resource.invoiceId(),
                resource.bookingId(),
                resource.userId(),
                resource.paymentMethodId()
        );
    }
}