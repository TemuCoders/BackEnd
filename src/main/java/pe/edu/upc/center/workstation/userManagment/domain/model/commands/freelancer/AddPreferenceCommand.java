package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record AddPreferenceCommand(Long freelancerId, String tag) {

}
