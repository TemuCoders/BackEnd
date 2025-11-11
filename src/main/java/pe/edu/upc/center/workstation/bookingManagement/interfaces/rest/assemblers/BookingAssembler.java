package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.assemblers;

import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.ChangeStatusBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.CreateBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingStatus;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.bookingManagement.infrastructure.persistance.jpa.repositories.BookingRepository;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.BookingMinimalResponse;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.BookingResponse;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.ChangeStatusBookingRequest;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.CreateBookingRequest;

public class BookingAssembler {


    public static CreateBookingCommand toCommandFromRequest(CreateBookingRequest request){
        return new CreateBookingCommand(
                new FreelancerId(request.freelancerId()), new SpaceId(request.spaceId()),
                request.bookingDate(), request.startDate(), request.endDate()
        );
    }

    public static ChangeStatusBookingCommand toCommandFromRequest(String bookingCode, ChangeStatusBookingRequest request){
        return new ChangeStatusBookingCommand(new BookingCode(bookingCode), new BookingStatus(request.bookingStatus()));
    }

    public static BookingResponse toResponseFromEntity(Booking entity){

        return new BookingResponse(entity.getCode().code(),
                entity.getFreelancerId().freelancerId(),
                entity.getSpaceId().spaceId(),
                entity.getBookingDate(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    public static BookingMinimalResponse toMinimalResponseFromEntity(Booking entity){
        return new BookingMinimalResponse(entity.getCode().code(),
                entity.getSpaceId().spaceId(),
                entity.getStartDate(),
                entity.getEndDate());
    }
}
