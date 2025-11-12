package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;

import java.util.Optional;

public interface InvoiceCommandService {
    Long handle(CreateInvoiceCommand command);
    Optional<Invoice> handle(SendInvoiceCommand command);
}