package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

import java.util.List;

public record CreateSpaceCommand(
        String name,
        SpaceType spaceType,
        Long ownerId,
        Double price,
        Double capacity,
        String description,
        Boolean available
) { }