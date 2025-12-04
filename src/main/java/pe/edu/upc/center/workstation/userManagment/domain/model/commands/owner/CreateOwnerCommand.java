package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;


public record CreateOwnerCommand(Long userId,String company, String ruc) {}
