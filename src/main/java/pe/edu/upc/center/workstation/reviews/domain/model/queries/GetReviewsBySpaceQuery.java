package pe.edu.upc.center.workstation.reviews.domain.model.queries;

public record GetReviewsBySpaceQuery(Long spaceId, int page, int size) {
    public GetReviewsBySpaceQuery {
        if (spaceId == null || spaceId <= 0) {
            throw new IllegalArgumentException("SpaceId is required and must be positive");
        }
        if (page < 0) {
            throw new IllegalArgumentException("Page cannot be negative");
        }
        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
    }
}