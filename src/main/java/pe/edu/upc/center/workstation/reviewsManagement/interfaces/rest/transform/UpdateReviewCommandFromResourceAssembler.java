package pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.transform;

import pe.edu.upc.center.workstation.reviewsManagement.domain.model.commands.UpdateReviewCommand;
import pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.resources.UpdateReviewResource;

public class UpdateReviewCommandFromResourceAssembler {

    public static UpdateReviewCommand toCommandFromResource(Long reviewId, UpdateReviewResource resource) {
        return new UpdateReviewCommand(reviewId, resource.rating(), resource.comment());
    }
}