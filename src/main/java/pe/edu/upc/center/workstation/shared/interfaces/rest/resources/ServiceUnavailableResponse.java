package pe.edu.upc.center.workstation.shared.interfaces.rest.resources;

public record ServiceUnavailableResponse(
    int status, String error, String message
) {
}
