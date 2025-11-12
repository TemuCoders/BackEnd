package pe.edu.upc.center.workstation.reviews.domain.model.commands;

/**
 * Command: intención de eliminar (soft delete) una Review.
 */
public record DeleteReviewCommand(Long reviewId) {
    public DeleteReviewCommand {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("ReviewId is required and must be positive");
        }
    }
}