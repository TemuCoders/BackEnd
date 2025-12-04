package pe.edu.upc.center.workstation.bookingManagement.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.FreelancerId;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.FreelancerContextFacade;

@Service
public class ExternalFreelancerService {
    private final FreelancerContextFacade freelancerContextFacade;

    public ExternalFreelancerService(FreelancerContextFacade freelancerContextFacade) {
        this.freelancerContextFacade = freelancerContextFacade;
    }

    public boolean existsFreelancerById(FreelancerId freelancerId) {
        return this.freelancerContextFacade.existsFreelancerById(freelancerId.freelancerId());
    }
}