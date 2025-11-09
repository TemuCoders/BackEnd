package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record DeleteFreelancerCommand(Long freelancerId) {
    public DeleteFreelancerCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[DeleteFreelancerCommand] freelancerId must be > 0");
    }
}
