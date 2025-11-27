package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record UpdateFreelancerUserTypeCommand(Long freelancerId, String userType) {

}
