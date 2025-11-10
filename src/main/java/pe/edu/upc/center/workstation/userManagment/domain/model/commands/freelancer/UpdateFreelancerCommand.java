package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record UpdateFreelancerCommand(Long freelancerId, String userType) {
    public UpdateFreelancerCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[UpdateFreelancerCommand] freelancerId must be > 0");
        if (Objects.isNull(userType) || userType.isBlank())
            throw new IllegalArgumentException("[UpdateFreelancerCommand] userType must not be blank");
        if (userType.trim().length() > 50)
            throw new IllegalArgumentException("[UpdateFreelancerCommand] userType max length is 50");
    }
}
