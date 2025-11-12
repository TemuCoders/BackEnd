package pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands;

/**
 * Command: intención de enviar una factura.
 */
public record SendInvoiceCommand(Long invoiceId) {
    public SendInvoiceCommand {
        if (invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("InvoiceId is required and must be positive");
        }
    }
}