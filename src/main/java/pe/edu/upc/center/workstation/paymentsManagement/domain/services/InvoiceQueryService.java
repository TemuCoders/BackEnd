package pe.edu.upc.center.workstation.paymentsManagement.domain.services;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;

import java.util.Optional;

public interface InvoiceQueryService {
    Optional<Invoice> handle(GetInvoiceByIdQuery query);
    Optional<Invoice> handle(GetInvoiceByBookingIdQuery query);
}