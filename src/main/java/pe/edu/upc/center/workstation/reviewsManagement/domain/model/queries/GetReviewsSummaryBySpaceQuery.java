package pe.edu.upc.center.workstation.reviewsManagement.domain.model.queries;

public record GetReviewsSummaryBySpaceQuery(Long spaceId) {
    public GetReviewsSummaryBySpaceQuery {
        if (spaceId == null || spaceId <= 0) {
            throw new IllegalArgumentException("SpaceId is required and must be positive");
        }
    }
}