package pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(EmailAddress emailAddress);
    boolean existsByEmail(EmailAddress emailAddress);
    List<User> findByRole_RoleName(UserRoleName roleName);

}
