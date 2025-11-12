package pe.edu.upc.center.workstation.reviews.infrastructure.acl.dto;

/**
 * DTO mínimo para User del otro bounded context.
 */
public record UserMinimalDto(Long id, String name) {
}