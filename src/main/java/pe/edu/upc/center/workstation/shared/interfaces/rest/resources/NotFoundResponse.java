package pe.edu.upc.center.workstation.shared.interfaces.rest.resources;

public record NotFoundResponse(
    int status, String error, String message
) {
}
