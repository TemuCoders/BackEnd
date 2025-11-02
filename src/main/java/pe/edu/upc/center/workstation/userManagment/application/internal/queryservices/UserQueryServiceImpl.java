package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override public List<User> handle(GetAllUsersQuery q) { return userRepository.findAll(); }
    @Override public Optional<User> handle(GetUserByIdQuery q) { return userRepository.findById((long) q.userId()); }
    @Override public Optional<User> handle(GetUserByEmailQuery q) { return userRepository.findByEmail(q.email()); }
}
