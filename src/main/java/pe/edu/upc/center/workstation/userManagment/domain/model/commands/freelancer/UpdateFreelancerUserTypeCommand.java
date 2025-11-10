package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record UpdateFreelancerUserTypeCommand(Long freelancerId, String userType) {
    public UpdateFreelancerUserTypeCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[UpdateFreelancerUserTypeCommand] freelancerId must be > 0");
        if (Objects.isNull(userType) || userType.isBlank())
            throw new IllegalArgumentException("[UpdateFreelancerUserTypeCommand] userType must not be blank");
        if (userType.trim().length() > 50)
            throw new IllegalArgumentException("[UpdateFreelancerUserTypeCommand] userType max length is 50");
    }
}
