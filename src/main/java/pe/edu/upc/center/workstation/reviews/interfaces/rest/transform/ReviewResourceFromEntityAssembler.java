package pe.edu.upc.center.workstation.reviews.interfaces.rest.transform;

import pe.edu.upc.center.workstation.reviews.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {

    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
                entity.getId(),
                entity.getSpaceId(),
                entity.getUserId(),
                entity.getRatingValue(),
                entity.getComment(),
                entity.getStatus().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}