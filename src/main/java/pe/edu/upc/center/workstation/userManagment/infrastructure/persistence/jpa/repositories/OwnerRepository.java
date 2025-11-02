package pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;


import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByRuc(String ruc);

    boolean existsByRuc(String ruc);

    boolean existsByRucAndIdIsNot(String ruc, Long id);
}
