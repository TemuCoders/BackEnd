package pe.edu.upc.center.workstation.reviews.domain.services;

import pe.edu.upc.center.workstation.reviews.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviews.domain.model.commands.*;

import java.util.Optional;

public interface ReviewCommandService {
    Long handle(CreateReviewCommand command);
    Optional<Review> handle(UpdateReviewCommand command);
    void handle(DeleteReviewCommand command);
}