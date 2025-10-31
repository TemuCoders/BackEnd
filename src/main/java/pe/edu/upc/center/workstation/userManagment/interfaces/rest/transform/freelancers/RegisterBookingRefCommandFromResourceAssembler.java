package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.RegisterBookingRefCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.RegisterBookingRefResource;

public final class RegisterBookingRefCommandFromResourceAssembler {
    private RegisterBookingRefCommandFromResourceAssembler() {}
    public static RegisterBookingRefCommand toCommand(Long freelancerId, RegisterBookingRefResource r) {
        return new RegisterBookingRefCommand(freelancerId, r.bookingId());
    }
}
