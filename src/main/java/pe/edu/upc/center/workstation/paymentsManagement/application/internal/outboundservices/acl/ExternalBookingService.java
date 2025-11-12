package pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.BookingId;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl.BookingAclClient;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;
import org.springframework.stereotype.Service;

@Service
public class ExternalBookingService {

    private final BookingAclClient bookingAclClient;

    public ExternalBookingService(BookingAclClient bookingAclClient) {
        this.bookingAclClient = bookingAclClient;
    }

    public void validateBookingExists(BookingId bookingId) {
        if (!bookingAclClient.existsBooking(bookingId.bookingId())) {
            throw new NotFoundArgumentException("Booking not found with id: " + bookingId.bookingId());
        }
    }

    public void validateBookingExists(Long bookingId) {
        validateBookingExists(new BookingId(bookingId));
    }
}