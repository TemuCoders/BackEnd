package pe.edu.upc.center.workstation.bookingManagement.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.bookingManagement.application.internal.outboundservices.acl.ExternalFreelancerService;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.ChangeStatusBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.CreateBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.DeleteBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingCommandService;
import pe.edu.upc.center.workstation.bookingManagement.infrastructure.persistance.jpa.repositories.BookingRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;

import java.util.Optional;

@Service
public class BookingCommandServiceImpl implements BookingCommandService {

    private final BookingRepository bookingRepository;
    private final ExternalFreelancerService externalFreelancerService;

    public BookingCommandServiceImpl(BookingRepository bookingRepository, ExternalFreelancerService externalFreelancerService) {
        this.bookingRepository = bookingRepository;
        this.externalFreelancerService = externalFreelancerService;
    }

    @Override
    public BookingCode handle(CreateBookingCommand command){
        var freelancerId = command.freelancerId();

        if(this.bookingRepository.existsByFreelancerId(freelancerId)){
            throw new IllegalStateException("Booking already exists");
        }

        if (!this.externalFreelancerService.existsFreelancerById(freelancerId)) {
            throw new NotFoundArgumentException(
                    String.format("Freelancer ID: %s, not found in external Freelacner service: ", freelancerId.freelancerId()));
        }

        var booking = new Booking(command);
        try {
            var createdBooking = this.bookingRepository.save(booking);
            return createdBooking.getCode();
        } catch (Exception e) {
            throw new PersistenceException("Error while creating booking: " + e.getMessage());
        }
    }

    @Override
    public Optional<Booking> handle(ChangeStatusBookingCommand command){
        var bookingToUpdate = this.bookingRepository.findByCode(command.bookingCode())
                .orElseThrow(() -> new NotFoundArgumentException(
                String.format("Booking not found with code: %s ", command.bookingCode().code())));

        bookingToUpdate.updateBookingStatus(command);

        try{
            var updatedBooking = this.bookingRepository.save(bookingToUpdate);
            return Optional.of(updatedBooking);
        }
        catch (Exception e){
            throw new PersistenceException("Error while updating booking status: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteBookingCommand command){
        if (!this.bookingRepository.existsByCode(command.bookingCode())){
            throw new NotFoundArgumentException(
                    String.format("Booking not found with code: %s ", command.bookingCode().code()));
        }

        try {
            this.bookingRepository.findByCode(command.bookingCode()).ifPresent(optionalBooking -> {
                this.bookingRepository.deleteById(optionalBooking.getId());
            });
        } catch (Exception e) {
            throw new PersistenceException("Error while deleting booking: " + e.getMessage());
        }
    }
}
