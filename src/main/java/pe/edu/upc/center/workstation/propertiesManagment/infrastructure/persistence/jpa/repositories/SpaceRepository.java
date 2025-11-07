package pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    List<Space> findByName(String name);

    List<Space> findByOwnerId(OwnerId id);

}
