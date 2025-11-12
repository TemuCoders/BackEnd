package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.CreateInvoiceCommand;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.CreateInvoiceCommand.InvoiceLineCommand;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.CreateInvoiceResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateInvoiceCommandFromResourceAssembler {

    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {

        List<InvoiceLineCommand> lineCommands = resource.lines().stream()
                .map(lineRes -> new InvoiceLineCommand(
                        lineRes.description(),
                        lineRes.quantity(),
                        lineRes.unitAmount(),
                        lineRes.tax()
                ))
                .collect(Collectors.toList());

        return new CreateInvoiceCommand(
                resource.bookingId(),
                resource.userId(),
                resource.currency(),
                lineCommands
        );
    }
}