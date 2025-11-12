package pe.edu.upc.center.workstation.reviewsManagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(name = "ReviewResponse", description = "Review information")
public record ReviewResource(
        @Schema(description = "Review ID")
        Long id,

        @Schema(description = "Space ID")
        Long spaceId,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "Rating (1-5)")
        int rating,

        @Schema(description = "Review comment")
        String comment,

        @Schema(description = "Review status")
        String status,

        @Schema(description = "Creation timestamp")
        Date createdAt,

        @Schema(description = "Last update timestamp")
        Date updatedAt
) {
}