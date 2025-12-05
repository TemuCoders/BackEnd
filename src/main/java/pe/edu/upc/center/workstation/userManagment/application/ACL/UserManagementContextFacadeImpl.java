package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.CreateFreelancerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.RegisterUserCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.GetUserByEmailQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.UserManagementContextFacade;

@Service
public class UserManagementContextFacadeImpl implements UserManagementContextFacade {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final OwnerCommandService ownerCommandService;
    private final FreelancerCommandService freelancerCommandService;

    public UserManagementContextFacadeImpl(UserCommandService userCommandService,
                                           UserQueryService userQueryService,
                                           OwnerCommandService ownerCommandService,
                                           FreelancerCommandService freelancerCommandService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.ownerCommandService = ownerCommandService;
        this.freelancerCommandService = freelancerCommandService;
    }

    @Override
    @Transactional
    public Long registerUser(String name,
                             String email,
                             String password,
                             String photo,
                             int age,
                             String location,
                             String roleName) {

        // Crear usuario primero
        var created = userCommandService.handle(
                new RegisterUserCommand(
                        name,
                        email,
                        password,
                        photo,
                        age,
                        location,
                        UserRoleName.valueOf(roleName.toUpperCase())
                )
        );

        if (created.isEmpty()) return 0L;

        Long userId = created.get().getId();

        if (roleName.equalsIgnoreCase("OWNER")) {
            String ruc = String.format("%011d", userId + 10000000000L);
            ownerCommandService.handle(new CreateOwnerCommand(userId, "Default Company", ruc));
        } else if (roleName.equalsIgnoreCase("FREELANCER")) {
            freelancerCommandService.handle(new CreateFreelancerCommand(userId, "Default"));
        }

        return userId;
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
