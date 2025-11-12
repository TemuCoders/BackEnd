package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetInvoiceByIdQuery(Long invoiceId) {
    public GetInvoiceByIdQuery {
        if (invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("InvoiceId is required and must be positive");
        }
    }
}