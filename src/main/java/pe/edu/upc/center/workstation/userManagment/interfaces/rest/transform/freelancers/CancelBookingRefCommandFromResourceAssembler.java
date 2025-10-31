package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.CancelBookingRefCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.CancelBookingRefResource;

public final class CancelBookingRefCommandFromResourceAssembler {
    private CancelBookingRefCommandFromResourceAssembler() {}
    public static CancelBookingRefCommand toCommand(Long freelancerId, CancelBookingRefResource r) {
        return new CancelBookingRefCommand(freelancerId, r.bookingId());
    }
}
