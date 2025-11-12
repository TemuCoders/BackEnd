package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.AddPaymentMethodCommand;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.AddPaymentMethodResource;

public class AddPaymentMethodCommandFromResourceAssembler {

    public static AddPaymentMethodCommand toCommandFromResource(AddPaymentMethodResource resource) {
        return new AddPaymentMethodCommand(
                resource.userId(),
                resource.type(),
                resource.maskedPan(),
                resource.expMonth(),
                resource.expYear()
        );
    }
}