package pe.edu.upc.center.workstation.servicesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;

import java.util.Objects;

public record CreateServiceCommand(
        SpaceId spaceId,
        String name,
        String description,
        Double price
) {
    public CreateServiceCommand {
        Objects.requireNonNull(spaceId, "[CreateServiceCommand] spaceId is required]");
        Objects.requireNonNull(name, "[CreateServiceCommand] name is required]");
        Objects.requireNonNull(description, "[CreateServiceCommand] description is required]");
        Objects.requireNonNull(price, "[CreateServiceCommand] price is required]");
    }
}
