package pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object que representa el número único de una factura.
 * Formato: INV-00001, INV-00002, etc.
 */
@Embeddable
public record InvoiceNumber(String value) {

    public InvoiceNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Invoice number cannot be empty");
        }
        if (!value.matches("^INV-\\d{5}$")) {
            throw new IllegalArgumentException("Invoice number must follow format: INV-XXXXX");
        }
    }

    public InvoiceNumber() {
        this("INV-00000");
    }

    /**
     * Genera el siguiente número de factura.
     * Ejemplo: INV-00001 -> INV-00002
     */
    public static InvoiceNumber generate(String lastInvoiceNumber) {
        if (lastInvoiceNumber == null || lastInvoiceNumber.isBlank()) {
            return new InvoiceNumber("INV-00001");
        }

        String[] parts = lastInvoiceNumber.split("-");
        int number = Integer.parseInt(parts[1]);
        int nextNumber = number + 1;

        return new InvoiceNumber(String.format("INV-%05d", nextNumber));
    }
}