package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.FreelancerContextFacade;

@Service
public class FreelancersContextFacadeImpl implements FreelancerContextFacade {

    private final FreelancerCommandService freelancerCommandService;
    private final FreelancerQueryService freelancerQueryService;

    public FreelancersContextFacadeImpl(
            FreelancerCommandService freelancerCommandService,
            FreelancerQueryService freelancerQueryService) {
        this.freelancerCommandService = freelancerCommandService;
        this.freelancerQueryService = freelancerQueryService;
    }

    @Override
    public Long createFreelancer(String userType) {
        var cmd = new CreateFreelancerCommand(userType);
        Long freelancerId = freelancerCommandService.handle(cmd);
        return (freelancerId == null) ? 0L : freelancerId;
    }

    @Override
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
