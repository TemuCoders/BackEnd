package pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Aggregate Root: PaymentMethod
 *
 * Representa un método de pago de un usuario.
 */
@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends AuditableAbstractAggregateRoot<PaymentMethod> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    })
    private UserId userId;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private PaymentMethodType type;

    @Getter
    @Column(name = "masked_pan", length = 20)
    private String maskedPan;

    @Getter
    @Column(name = "exp_month")
    private Integer expMonth;

    @Getter
    @Column(name = "exp_year")
    private Integer expYear;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private PaymentMethodStatus status;

    // Constructor vacío para JPA
    protected PaymentMethod() {
    }

    // Constructor para crear PaymentMethod
    public PaymentMethod(UserId userId, PaymentMethodType type,
                         String maskedPan, Integer expMonth, Integer expYear) {
        super();

        this.userId = userId;
        this.type = type;
        this.status = PaymentMethodStatus.ACTIVE;

        // Validación específica para tarjetas
        if (type == PaymentMethodType.CARD) {
            if (maskedPan == null || maskedPan.isBlank()) {
                throw new IllegalArgumentException("Masked PAN is required for CARD type");
            }
            if (expMonth == null || expMonth < 1 || expMonth > 12) {
                throw new IllegalArgumentException("Expiration month must be between 1 and 12");
            }
            if (expYear == null || expYear < 2024) {
                throw new IllegalArgumentException("Expiration year must be valid");
            }

            this.maskedPan = maskedPan;
            this.expMonth = expMonth;
            this.expYear = expYear;
        }
    }

    /**
     * Método de dominio: deshabilitar método de pago.
     */
    public void disable() {
        if (status == PaymentMethodStatus.DISABLED) {
            throw new IllegalStateException("Payment method already disabled");
        }
        this.status = PaymentMethodStatus.DISABLED;
    }

    /**
     * Método de dominio: habilitar método de pago.
     */
    public void enable() {
        if (status == PaymentMethodStatus.ACTIVE) {
            throw new IllegalStateException("Payment method already active");
        }
        this.status = PaymentMethodStatus.ACTIVE;
    }

    // Getters
    public Long getUserId() {
        return userId.userId();
    }

    public boolean isActive() {
        return status == PaymentMethodStatus.ACTIVE;
    }
}