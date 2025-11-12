package pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries;

public record GetInvoiceByBookingIdQuery(Long bookingId) {
    public GetInvoiceByBookingIdQuery {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("BookingId is required and must be positive");
        }
    }
}