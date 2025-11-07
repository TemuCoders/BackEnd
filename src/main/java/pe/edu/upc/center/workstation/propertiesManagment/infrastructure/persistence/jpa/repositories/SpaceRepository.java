package pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

}
