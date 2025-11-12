package pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl;

import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl.dto.BookingMinimalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Component
public class BookingAclClient {

    private static final Logger log = LoggerFactory.getLogger(BookingAclClient.class);

    private final RestClient restClient;
    private final String baseUrl;

    public BookingAclClient(
            RestClient.Builder restClientBuilder,
            @Value("${app.bookings.api.base-url:http://localhost:8091}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public Optional<BookingMinimalDto> getBookingMinimal(Long bookingId) {
        try {
            log.info("ACL: Calling GET {}/api/v1/bookings/{}", baseUrl, bookingId);

            BookingMinimalDto booking = restClient.get()
                    .uri("/api/v1/bookings/{bookingId}", bookingId)
                    .retrieve()
                    .body(BookingMinimalDto.class);

            log.info("ACL: Booking found: {}", booking);
            return Optional.ofNullable(booking);

        } catch (RestClientException e) {
            log.warn("ACL: Booking not found or service unavailable: {}", bookingId);
            return Optional.empty();
        }
    }

    public boolean existsBooking(Long bookingId) {
        return getBookingMinimal(bookingId).isPresent();
    }
}