package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.RemovePreferenceCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.RemovePreferenceResource;

public final class RemovePreferenceCommandFromResourceAssembler {
    private RemovePreferenceCommandFromResourceAssembler() {}
    public static RemovePreferenceCommand toCommand(Long freelancerId, RemovePreferenceResource r) {
        return new RemovePreferenceCommand(freelancerId, r.tag());
    }
}