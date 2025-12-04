package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.SetUserRoleCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.FreelancerContextFacade;

@Service
public class FreelancersContextFacadeImpl implements FreelancerContextFacade {

    private final FreelancerCommandService freelancerCommandService;
    private final FreelancerQueryService freelancerQueryService;
    private final UserCommandService userCommandService;

    public FreelancersContextFacadeImpl(FreelancerCommandService freelancerCommandService,
                                        FreelancerQueryService freelancerQueryService,
                                        UserCommandService userCommandService) {
        this.freelancerCommandService = freelancerCommandService;
        this.freelancerQueryService = freelancerQueryService;
        this.userCommandService = userCommandService;
    }

    @Override
    @Transactional
    public Long createFreelancer(Long userId, String userType) {
        Long freelancerId = freelancerCommandService.handle(new CreateFreelancerCommand(userId, userType));
        userCommandService.handle(new SetUserRoleCommand(userId, UserRoleName.FREELANCER));

        return freelancerId;
    }

    @Override
    @Transactional
    public void updateFreelancerUserType(Long freelancerId, String userType) {
        freelancerCommandService.handle(new UpdateFreelancerUserTypeCommand(freelancerId, userType));
    }

    @Override
    public void addPreference(Long freelancerId, String tag) {
        freelancerCommandService.handle(new AddPreferenceCommand(freelancerId, tag));
    }

    @Override
    public void removePreference(Long freelancerId, String tag) {
        freelancerCommandService.handle(new RemovePreferenceCommand(freelancerId, tag));
    }

    @Override
    public void addFavoriteSpace(Long freelancerId, Long spaceId) {
        freelancerCommandService.handle(new AddFavoriteSpaceCommand(freelancerId, spaceId));
    }

    @Override
    public void removeFavoriteSpace(Long freelancerId, Long spaceId) {
        freelancerCommandService.handle(new RemoveFavoriteSpaceCommand(freelancerId, spaceId));
    }

    @Override
    public boolean existsFreelancerById(Long freelancerId) {
        return freelancerQueryService.handle(new ExistsFreelancerByIdQuery(freelancerId));
    }
}

