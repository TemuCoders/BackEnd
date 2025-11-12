package pe.edu.upc.center.workstation.reviews.domain.model.commands;

/**
 * Command: intención de crear una nueva Review.
 */
public record CreateReviewCommand(
        Long spaceId,
        Long userId,
        int rating,
        String comment
) {
    public CreateReviewCommand {
        if (spaceId == null || spaceId <= 0) {
            throw new IllegalArgumentException("SpaceId is required and must be positive");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment is required");
        }
    }
}