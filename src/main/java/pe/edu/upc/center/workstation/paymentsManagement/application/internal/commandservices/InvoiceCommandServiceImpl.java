package pe.edu.upc.center.workstation.paymentsManagement.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Qualifier;
import pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl.ExternalBookingService;
import pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl.ExternalUserService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.entities.InvoiceLine;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.InvoiceCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {

    private final InvoiceRepository invoiceRepository;
    private final ExternalUserService externalUserService;
    private final ExternalBookingService externalBookingService;

    public InvoiceCommandServiceImpl(
            InvoiceRepository invoiceRepository,
            @Qualifier("paymentsExternalUserService") ExternalUserService externalUserService,
            ExternalBookingService externalBookingService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.externalUserService = externalUserService;
        this.externalBookingService = externalBookingService;
    }

    @Override
    @Transactional
    public Long handle(CreateInvoiceCommand command) {
        // 1. Validar que User existe (ACL)
        externalUserService.validateUserExists(command.userId());

        // 2. Validar que Booking existe (ACL)
        externalBookingService.validateBookingExists(command.bookingId());

        // 3. Verificar que no exista ya una factura para este booking
        var existingInvoice = invoiceRepository.findByBookingId(command.bookingId());
        if (existingInvoice.isPresent()) {
            throw new IllegalArgumentException(
                    "Invoice already exists for booking: " + command.bookingId()
            );
        }

        // 4. Generar número de factura
        String lastInvoiceNumber = invoiceRepository.findLastInvoiceNumber()
                .orElse(null);
        InvoiceNumber invoiceNumber = InvoiceNumber.generate(lastInvoiceNumber);

        // 5. Crear líneas de factura
        List<InvoiceLine> lines = command.lines().stream()
                .map(lineCmd -> new InvoiceLine(
                        lineCmd.description(),
                        lineCmd.quantity(),
                        Money.of(lineCmd.unitAmount(), command.currency()),
                        Money.of(lineCmd.tax(), command.currency())
                ))
                .collect(Collectors.toList());

        // 6. Crear agregado Invoice
        var invoice = new Invoice(
                invoiceNumber,
                new BookingId(command.bookingId()),
                new UserId(command.userId()),
                command.currency(),
                lines
        );

        // 7. Emitir automáticamente la factura
        invoice.issue();

        // 8. Persistir
        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving invoice: " + e.getMessage());
        }

        return invoice.getId();
    }

    @Override
    @Transactional
    public Optional<Invoice> handle(SendInvoiceCommand command) {
        // 1. Buscar factura
        var invoice = invoiceRepository.findById(command.invoiceId())
                .orElseThrow(() -> new NotFoundIdException(Invoice.class, command.invoiceId()));

        // 2. Enviar (método de dominio)
        invoice.send();

        // 3. Persistir
        try {
            var updated = invoiceRepository.save(invoice);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error sending invoice: " + e.getMessage());
        }
    }
}