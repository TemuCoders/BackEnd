package pe.edu.upc.center.workstation.shared.interfaces.rest.resources;

public record InternaServerErrorResponse(
    int status, String error, String message
) {
}
