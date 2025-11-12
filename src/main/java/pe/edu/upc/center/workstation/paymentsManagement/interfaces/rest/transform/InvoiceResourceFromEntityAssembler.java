package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.entities.InvoiceLine;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.InvoiceResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.InvoiceResource.InvoiceLineDetailResource;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceResourceFromEntityAssembler {

    public static InvoiceResource toResourceFromEntity(Invoice entity) {

        List<InvoiceLineDetailResource> lineResources = entity.getLines().stream()
                .map(InvoiceResourceFromEntityAssembler::toLineResource)
                .collect(Collectors.toList());

        return new InvoiceResource(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getBookingId(),
                entity.getUserId(),
                entity.getCurrency(),
                entity.getSubtotal().amount(),
                entity.getTax().amount(),
                entity.getTotal().amount(),
                entity.getStatus().name(),
                entity.getIssuedAt(),
                entity.getSentAt(),
                lineResources,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private static InvoiceLineDetailResource toLineResource(InvoiceLine line) {
        return new InvoiceLineDetailResource(
                line.getId(),
                line.getDescription(),
                line.getQuantity(),
                line.getUnitAmount().amount(),
                line.getTax().amount(),
                line.getSubtotal().amount(),
                line.getTotal().amount()
        );
    }
}