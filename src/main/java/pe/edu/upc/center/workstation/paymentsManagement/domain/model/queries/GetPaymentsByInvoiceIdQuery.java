package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetPaymentsByInvoiceIdQuery(Long invoiceId) {
    public GetPaymentsByInvoiceIdQuery {
        if (invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("InvoiceId is required and must be positive");
        }
    }
}