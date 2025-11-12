package pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates;

import pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions.InvoiceCurrencyMismatchException;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.entities.InvoiceLine;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Aggregate Root: Invoice
 *
 * Representa una factura con sus líneas de detalle.
 *
 * Invariantes:
 * - Todas las líneas deben tener la misma moneda que la factura
 * - El total debe ser la suma de todas las líneas
 */
@Entity
@Table(name = "invoices")
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "invoice_number", unique = true, nullable = false, length = 32))
    })
    private InvoiceNumber invoiceNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "bookingId", column = @Column(name = "booking_id", nullable = false))
    })
    private BookingId bookingId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    })
    private UserId userId;

    @Getter
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "subtotal_currency", length = 3, nullable = false, insertable = false, updatable = false))
    })
    private Money subtotal;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "tax", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "tax_currency", length = 3, nullable = false, insertable = false, updatable = false))
    })
    private Money tax;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency", length = 3, nullable = false, insertable = false, updatable = false))
    })
    private Money total;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private InvoiceStatus status;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issued_at")
    private Date issuedAt;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_at")
    private Date sentAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id", nullable = false)
    private List<InvoiceLine> lines = new ArrayList<>();

    // Constructor vacío para JPA
    protected Invoice() {
    }

    // Constructor para crear Invoice
    public Invoice(InvoiceNumber invoiceNumber, BookingId bookingId, UserId userId,
                   String currency, List<InvoiceLine> lines) {
        super();

        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Invoice must have at least one line");
        }

        this.invoiceNumber = invoiceNumber;
        this.bookingId = bookingId;
        this.userId = userId;
        this.currency = currency;
        this.lines = new ArrayList<>(lines);
        this.status = InvoiceStatus.DRAFT;

        // Calcular totales
        calculateTotals();
    }

    /**
     * Método de dominio: calcular totales y validar monedas.
     */
    private void calculateTotals() {
        Money calculatedSubtotal = Money.zero(currency);
        Money calculatedTax = Money.zero(currency);

        for (InvoiceLine line : lines) {
            // Validar que todas las líneas tengan la misma moneda
            if (!line.getUnitAmount().currency().equals(currency)) {
                throw new InvoiceCurrencyMismatchException(
                        "All invoice lines must have currency: " + currency
                );
            }

            calculatedSubtotal = calculatedSubtotal.add(line.getSubtotal());
            calculatedTax = calculatedTax.add(line.getTax().multiply(line.getQuantity()));
        }

        this.subtotal = calculatedSubtotal;
        this.tax = calculatedTax;
        this.total = calculatedSubtotal.add(calculatedTax);
    }

    /**
     * Método de dominio: emitir la factura.
     */
    public void issue() {
        if (status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT invoices can be issued");
        }
        this.status = InvoiceStatus.ISSUED;
        this.issuedAt = new Date();
    }

    /**
     * Método de dominio: enviar la factura.
     */
    public void send() {
        if (status != InvoiceStatus.ISSUED) {
            throw new IllegalStateException("Only ISSUED invoices can be sent");
        }
        this.status = InvoiceStatus.SENT;
        this.sentAt = new Date();
    }

    /**
     * Método de dominio: marcar como pagada.
     */
    public void markAsPaid() {
        if (status == InvoiceStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark CANCELLED invoice as paid");
        }
        this.status = InvoiceStatus.PAID;
    }

    /**
     * Método de dominio: cancelar la factura.
     */
    public void cancel() {
        if (status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Cannot cancel PAID invoice");
        }
        this.status = InvoiceStatus.CANCELLED;
    }

    // Getters
    public String getInvoiceNumber() {
        return invoiceNumber.value();
    }

    public Long getBookingId() {
        return bookingId.bookingId();
    }

    public Long getUserId() {
        return userId.userId();
    }

    public List<InvoiceLine> getLines() {
        return Collections.unmodifiableList(lines);
    }
}