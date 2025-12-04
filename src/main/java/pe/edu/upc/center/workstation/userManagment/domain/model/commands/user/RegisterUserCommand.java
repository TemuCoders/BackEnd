package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;

public record RegisterUserCommand(
        String name,
        String email,
        String password,
        String photo,
        int age,
        String location,
        UserRoleName roleName // NUEVO: asigna rol al crear
) {}
