package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;

import java.util.Optional;

public interface FreelancerCommandService {
    Long handle(CreateFreelancerCommand command);
    Optional<Freelancer> handle(UpdateFreelancerCommand command);
    void handle(DeleteFreelancerCommand command);

    void handle(AddPreferenceCommand command);
    void handle(RemovePreferenceCommand command);

    void handle(RegisterBookingRefCommand command);
    void handle(CancelBookingRefCommand command);

    void handle(AddFavoriteSpaceCommand command);
    void handle(RemoveFavoriteSpaceCommand command);
    void handle(UpdateFreelancerUserTypeCommand c);
}
