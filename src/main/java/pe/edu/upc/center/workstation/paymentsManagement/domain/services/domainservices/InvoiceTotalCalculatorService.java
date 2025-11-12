package pe.edu.upc.center.workstation.paymentsManagement.domain.services.domainservices;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.entities.InvoiceLine;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.Money;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Domain Service: calcula totales de una factura.
 */
@Service
public class InvoiceTotalCalculatorService {

    /**
     * Calcula el subtotal (suma de líneas sin impuestos).
     */
    public Money calculateSubtotal(List<InvoiceLine> lines, String currency) {
        Money subtotal = Money.zero(currency);

        for (InvoiceLine line : lines) {
            subtotal = subtotal.add(line.getSubtotal());
        }

        return subtotal;
    }

    /**
     * Calcula el total de impuestos.
     */
    public Money calculateTotalTax(List<InvoiceLine> lines, String currency) {
        Money totalTax = Money.zero(currency);

        for (InvoiceLine line : lines) {
            totalTax = totalTax.add(line.getTax().multiply(line.getQuantity()));
        }

        return totalTax;
    }

    /**
     * Calcula el total (subtotal + impuestos).
     */
    public Money calculateTotal(List<InvoiceLine> lines, String currency) {
        Money subtotal = calculateSubtotal(lines, currency);
        Money tax = calculateTotalTax(lines, currency);
        return subtotal.add(tax);
    }
}