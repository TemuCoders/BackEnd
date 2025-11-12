package pe.edu.upc.center.workstation.paymentsManagement.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.aggregates.PaymentMethod;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.PaymentMethodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.userId.userId = :userId")
    List<PaymentMethod> findByUserId(@Param("userId") Long userId);

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.userId.userId = :userId AND pm.status = :status")
    List<PaymentMethod> findByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") PaymentMethodStatus status
    );
}