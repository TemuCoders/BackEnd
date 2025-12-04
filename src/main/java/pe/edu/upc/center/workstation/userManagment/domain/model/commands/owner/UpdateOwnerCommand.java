package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;


public record UpdateOwnerCommand(Long ownerId, String company, String ruc) {}
