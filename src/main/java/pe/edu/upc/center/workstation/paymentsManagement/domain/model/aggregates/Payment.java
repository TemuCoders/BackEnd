package pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates;

import pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions.InvalidPaymentStatusTransitionException;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

/**
 * Aggregate Root: Payment
 *
 * Representa un pago asociado a una factura.
 */
@Entity
@Table(name = "payments")
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", length = 3, nullable = false))
    })
    private Money amount;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private PaymentStatus status;

    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "paid_at")
    private Date paidAt;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "refunded_at")
    private Date refundedAt;

    // Constructor vacío para JPA
    protected Payment() {
    }

    // Constructor para crear Payment
    public Payment(Long invoiceId, BookingId bookingId, UserId userId,
                   Money amount, Long paymentMethodId) {
        super();

        if (amount.isNegative() || amount.isZero()) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethodId = paymentMethodId;
        this.status = PaymentStatus.PENDING;
    }

    /**
     * Método de dominio: marcar como pagado.
     */
    public void markAsPaid() {
        if (status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusTransitionException(status, PaymentStatus.PAID);
        }
        this.status = PaymentStatus.PAID;
        this.paidAt = new Date();
    }

    /**
     * Método de dominio: reembolsar.
     */
    public void refund() {
        if (status != PaymentStatus.PAID) {
            throw new InvalidPaymentStatusTransitionException(status, PaymentStatus.REFUNDED);
        }
        this.status = PaymentStatus.REFUNDED;
        this.refundedAt = new Date();
    }

    /**
     * Método de dominio: marcar como fallido.
     */
    public void markAsFailed() {
        if (status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusTransitionException(status, PaymentStatus.FAILED);
        }
        this.status = PaymentStatus.FAILED;
    }

    // Getters
    public Long getInvoiceId() {
        return invoiceId;
    }

    public Long getBookingId() {
        return bookingId.bookingId();
    }

    public Long getUserId() {
        return userId.userId();
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }
}