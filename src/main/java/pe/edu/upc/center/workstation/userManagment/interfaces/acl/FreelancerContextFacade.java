package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers.*;

import java.util.List;
import java.util.Optional;

@Service
public class FreelancerContextFacade {

    private final FreelancerCommandService freelancerCommandService;
    private final FreelancerQueryService freelancerQueryService;

    public FreelancerContextFacade(FreelancerCommandService freelancerCommandService,
                                   FreelancerQueryService freelancerQueryService) {
        this.freelancerCommandService = freelancerCommandService;
        this.freelancerQueryService = freelancerQueryService;
    }

    public Optional<FreelancerResource> fetchFreelancerById(Long freelancerId) {
        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId));
        return opt.map(FreelancerResourceFromEntityAssembler::toResource);
    }

    public List<FreelancerResource> fetchAllFreelancers() {
        return freelancerQueryService.handle(new GetAllFreelancersQuery())
                .stream()
                .map(FreelancerResourceFromEntityAssembler::toResource)
                .toList();
    }

    public List<Long> getFavoriteSpaceIds(Long freelancerId) {
        return freelancerQueryService.handle(new GetFreelancerFavoriteSpacesQuery(freelancerId));
    }

    public List<Long> getBookedSpaceIds(int freelancerId) {
        return freelancerQueryService.handle(new GetFreelancerBookedSpacesQuery(freelancerId));
    }

    public List<String> getPreferences(Long freelancerId) {
        return freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId))
                .map(Freelancer::getPreferences)
                .orElse(List.of());
    }

    public Long createFreelancer(CreateFreelancerResource resource) {
        var cmd = CreateFreelancerCommandFromResourceAssembler.toCommand(resource);
        return freelancerCommandService.handle(cmd);
    }

    public Optional<FreelancerResource> updateFreelancer(Long freelancerId, UpdateFreelancerResource resource) {
        var cmd = UpdateFreelancerCommandFromResourceAssembler.toCommand(freelancerId, resource);
        var updated = freelancerCommandService.handle(cmd);
        return updated.map(FreelancerResourceFromEntityAssembler::toResource);
    }

    public void deleteFreelancer(Long freelancerId) {
        freelancerCommandService.handle(new DeleteFreelancerCommand(freelancerId));
    }

    public void updateUserType(int freelancerId, String newType) {
        freelancerCommandService.handle(new UpdateFreelancerUserTypeCommand(freelancerId, newType));
    }

    public void addFavoriteSpace(Long freelancerId, long spaceId) {
        freelancerCommandService.handle(new AddFavoriteSpaceCommand(freelancerId, spaceId));
    }

    public void removeFavoriteSpace(Long freelancerId, long spaceId) {
        freelancerCommandService.handle(new RemoveFavoriteSpaceCommand(freelancerId, spaceId));
    }
}
