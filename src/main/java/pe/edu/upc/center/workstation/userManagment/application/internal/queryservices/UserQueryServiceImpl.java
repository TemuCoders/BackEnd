package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery q) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery q) {
        Long id = q.userId();
        return Optional.of(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundIdException(User.class, id))
        );
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery q) {
        return userRepository.findByEmail(q.email());
    }
}
