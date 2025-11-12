package pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByInvoiceId(Long invoiceId);
}