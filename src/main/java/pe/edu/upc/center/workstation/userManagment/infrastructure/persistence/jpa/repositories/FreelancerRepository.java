package pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
}
