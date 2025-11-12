package pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewSummaryResponse", description = "Summary statistics for reviews of a space")
public record ReviewSummaryResource(
        @Schema(description = "Average rating", example = "4.5")
        double averageRating,

        @Schema(description = "Total number of published reviews", example = "42")
        long totalCount
) {
}