package pe.edu.upc.center.workstation.reviews.domain.model.commands;

/**
 * Command: intención de actualizar una Review existente.
 */
public record UpdateReviewCommand(
        Long reviewId,
        int rating,
        String comment
) {
    public UpdateReviewCommand {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("ReviewId is required and must be positive");
        }
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment is required");
        }
    }
}