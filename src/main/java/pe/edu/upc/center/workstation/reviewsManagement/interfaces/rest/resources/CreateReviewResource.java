package pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateReviewRequest", description = "Request to create a new review")
public record CreateReviewResource(

        @Schema(description = "Space ID", example = "1")
        @NotNull(message = "Space ID is required")
        Long spaceId,

        @Schema(description = "User ID", example = "1")
        @NotNull(message = "User ID is required")
        Long userId,

        @Schema(description = "Rating (1-5)", example = "5", minimum = "1", maximum = "5")
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        int rating,

        @Schema(description = "Review comment (max 2000 chars)", example = "Excelente espacio")
        @NotBlank(message = "Comment is required")
        String comment
) {
}