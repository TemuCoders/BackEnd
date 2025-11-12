package pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.bookingId.bookingId = :bookingId")
    Optional<Invoice> findByBookingId(@Param("bookingId") Long bookingId);

    @Query("SELECT i.invoiceNumber.value FROM Invoice i ORDER BY i.id DESC LIMIT 1")
    Optional<String> findLastInvoiceNumber();
}