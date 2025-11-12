package pe.edu.upc.center.workstation.paymentsManagement.application.internal.queryservices;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.InvoiceQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByIdQuery query) {
        return invoiceRepository.findById(query.invoiceId());
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByBookingIdQuery query) {
        return invoiceRepository.findByBookingId(query.bookingId());
    }
}