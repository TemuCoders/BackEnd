package pe.edu.upc.center.workstation.reviews.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UpdateReviewRequest", description = "Request to update an existing review")
public record UpdateReviewResource(

        @Schema(description = "Rating (1-5)", example = "4")
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        int rating,

        @Schema(description = "Updated comment")
        @NotBlank(message = "Comment is required")
        String comment
) {
}