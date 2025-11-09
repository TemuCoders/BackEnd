package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record CreateFreelancerCommand(String userType) {
    public CreateFreelancerCommand {
        if (Objects.isNull(userType) || userType.isBlank())
            throw new IllegalArgumentException("[CreateFreelancerCommand] userType must not be blank");
        if (userType.trim().length() > 50)
            throw new IllegalArgumentException("[CreateFreelancerCommand] userType max length is 50");
    }
}
