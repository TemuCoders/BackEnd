package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import pe.edu.upc.center.workstation.shared.utils.Util;

import java.util.Objects;


public record CreateOwnerCommand(String company, String ruc) {}
