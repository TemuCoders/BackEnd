package pe.edu.upc.center.workstation.shared.interfaces.rest.resources;

public record InternalServerErrorResponse(
    int status, String error, String message
) {
}
