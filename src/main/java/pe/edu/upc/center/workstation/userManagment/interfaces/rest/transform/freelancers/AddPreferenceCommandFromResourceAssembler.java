package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.AddPreferenceCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.AddPreferenceResource;

public final class AddPreferenceCommandFromResourceAssembler {
    private AddPreferenceCommandFromResourceAssembler() {}
    public static AddPreferenceCommand toCommand(Long freelancerId, AddPreferenceResource r) {
        return new AddPreferenceCommand(freelancerId, r.tag());
    }
}
