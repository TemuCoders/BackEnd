package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Image(
        @Column(name = "image_id", nullable = false) Long id,
        @Column(name = "image_url", length = 255, nullable = false) String url
) {
    public Image {
        if (id == null || id <= 0) throw new IllegalArgumentException("Image id must be positive");
        if (url == null || url.isBlank()) throw new IllegalArgumentException("Image url is required");
    }
}
