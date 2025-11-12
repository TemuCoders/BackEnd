package pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.reviewsManagement.domain.model.commands.CreateReviewCommand;
import pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.resources.CreateReviewResource;

public class CreateReviewCommandFromResourceAssembler {

    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource) {
        return new CreateReviewCommand(
                resource.spaceId(),
                resource.userId(),
                resource.rating(),
                resource.comment()
        );
    }
}