package pe.edu.upc.center.workstation.servicesManagment.infraestructure.persistense.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {
    Optional<Service> findById(Long serviceId);
    List<Service> findBySpaceId(SpaceId spaceId);
}
