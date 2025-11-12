package pe.edu.upc.center.workstation.reviews.interfaces.rest.transform;

import pe.edu.upc.center.workstation.reviews.domain.model.commands.CreateReviewCommand;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.resources.CreateReviewResource;

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