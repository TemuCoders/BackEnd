package pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Spaces entities.
 */
@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    /**
     * Find a Space by their name.
     * @param name the space to search for.
     * @return an Optional containing the Space if found, or empty if not found.
     */
    Optional<Space> findByName(String name);

    List<Space> findByOwnerId(OwnerId id);


}
