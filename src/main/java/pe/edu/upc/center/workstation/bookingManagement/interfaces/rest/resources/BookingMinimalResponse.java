package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources;

import java.time.LocalDate;

public record BookingMinimalResponse(String bookingCode, Long spaceId, LocalDate startDate, LocalDate endDate) {
}
