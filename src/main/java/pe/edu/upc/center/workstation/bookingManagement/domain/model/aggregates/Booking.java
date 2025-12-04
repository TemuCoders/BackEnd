package pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.ChangeStatusBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.commands.CreateBookingCommand;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingStatus;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
@Table(name= "bookings")
public class Booking extends AuditableAbstractAggregateRoot<Booking> {

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="code",
            column = @Column(name = "code", length = 36, nullable = false)),
    })
    private BookingCode code;

    @Getter
    @Column(name="booking_date",nullable = false)
    private LocalDate bookingDate;

    @Getter
    @Future
    @Column(name="start_date",nullable = false)
    private LocalDate startDate;

    @Getter
    @Future
    @Column(name="end_date",nullable = false)
    private LocalDate endDate;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "status",
                    column = @Column(name = "status", length = 20, nullable = false))
    })
    private BookingStatus status = new BookingStatus("PENDING");

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "freelancerId",
                    column = @Column(name = "freelancer_id", nullable = false))
    })
    private FreelancerId freelancerId;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "spaceId",
                    column = @Column(name = "space_id", nullable = false))
    })
    private SpaceId spaceId;

    public Booking() {this.code = new BookingCode();}

    public Booking(CreateBookingCommand command){
        this();
        System.out.println("🧩 DEBUG BookingCommand: " + command);
        System.out.println("🧩 BookingDate: " + command.bookingDate());

        this.freelancerId = command.freelancerId();
        this.spaceId = command.spaceId();
        this.bookingDate = command.bookingDate();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
        this.status = new BookingStatus("PENDING");
    }

    public void updateBookingStatus(ChangeStatusBookingCommand command){
        this.code = command.bookingCode();
        this.status = command.bookingStatus();
    }
}

