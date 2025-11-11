package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources;

import java.time.LocalDate;

public record BookingResponse(String bookingCode, Long freelancerId, Long spaceId,LocalDate bookingDate, LocalDate startDate, LocalDate endDate) {
}
