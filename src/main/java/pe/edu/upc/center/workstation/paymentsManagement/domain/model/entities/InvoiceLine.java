package pe.edu.upc.center.workstation.paymentsManagement.domain.model.entities;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.Money;
import pe.edu.upc.center.workstation.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Entity: InvoiceLine (parte del agregado Invoice).
 * Representa una línea individual en una factura.
 */
@Entity
@Table(name = "invoice_lines")
public class InvoiceLine extends AuditableModel {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Getter
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "unit_amount", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", length = 3, nullable = false))
    })
    private Money unitAmount;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "tax_amount", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "tax_currency", length = 3, nullable = false))
    })
    private Money tax;

    // Constructor vacío para JPA
    protected InvoiceLine() {
    }

    // Constructor para crear línea
    public InvoiceLine(String description, int quantity, Money unitAmount, Money tax) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitAmount.isNegative()) {
            throw new IllegalArgumentException("Unit amount cannot be negative");
        }
        if (tax.isNegative()) {
            throw new IllegalArgumentException("Tax cannot be negative");
        }
        if (!unitAmount.currency().equals(tax.currency())) {
            throw new IllegalArgumentException("Unit amount and tax must have the same currency");
        }

        this.description = description.trim();
        this.quantity = quantity;
        this.unitAmount = unitAmount;
        this.tax = tax;
    }

    /**
     * Calcula el subtotal de esta línea (cantidad * precio unitario).
     */
    public Money getSubtotal() {
        return unitAmount.multiply(quantity);
    }

    /**
     * Calcula el total de esta línea (subtotal + impuestos).
     */
    public Money getTotal() {
        return getSubtotal().add(tax.multiply(quantity));
    }
}