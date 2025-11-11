package pe.edu.upc.center.workstation.bookingManagement.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.aggregates.Booking;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCode(BookingCode code);

    Optional<Booking> findByFreelancerId(FreelancerId freelancerId);

    boolean existsByCode(BookingCode code);

    boolean existsByFreelancerId(FreelancerId freelancerId);
}
