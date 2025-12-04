package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.ExistsFreelancerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerQueryService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.UserManagementContextFacade;


@Service
public class UserManagementContextFacadeImpl implements UserManagementContextFacade {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserManagementContextFacadeImpl(UserCommandService userCommandService,
                                           UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Override
    @Transactional
    public Long registerUser(String name,
                             String email,
                             String password,
                             String photo,
                             int age,
                             String location,
                             String roleName ) {

        var created = userCommandService.handle(
                new RegisterUserCommand(name, email, password, photo, age, location)
        );
        return created.map(u -> u.getId()).orElse(0L);
    }

    @Override
    @Transactional(readOnly = true)
    public Long fetchUserIdByEmail(String email) {
        var query = new GetUserByEmailQuery(new EmailAddress(email));
        var user = userQueryService.handle(query);
        return user.isEmpty() ? 0L : user.get().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByEmail(String email) {
        var query = new GetUserByEmailQuery(new EmailAddress(email));
        return userQueryService.handle(query).isPresent();
    }
}
