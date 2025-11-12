package pe.edu.upc.center.workstation.reviews.interfaces.rest.transform;

import pe.edu.upc.center.workstation.reviews.domain.model.commands.UpdateReviewCommand;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.resources.UpdateReviewResource;

public class UpdateReviewCommandFromResourceAssembler {

    public static UpdateReviewCommand toCommandFromResource(Long reviewId, UpdateReviewResource resource) {
        return new UpdateReviewCommand(reviewId, resource.rating(), resource.comment());
    }
}