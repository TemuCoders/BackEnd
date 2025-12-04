package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;


import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.CreateUserCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.RegisterUserRequest;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;

import java.util.regex.Pattern;

public final class CreateUserCommandFromResourceAssembler {

    private static final Pattern EMAIL_RX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$");

    private CreateUserCommandFromResourceAssembler() { }

    public static CreateUserCommand toCommand(RegisterUserRequest r) {
        if (r == null) {
            throw new IllegalArgumentException("[CreateUserCommand] request must not be null");
        }

        final String name = require(r.name(), "name");
        final String email = require(r.email(), "email");
        final String password = require(r.password(), "password");
        final String photo = require(r.photo(), "photo");
        final Integer age = r.age();
        final String location = require(r.location(), "location");

        if (!EMAIL_RX.matcher(email).matches()) {
            throw new IllegalArgumentException("[CreateUserCommand] invalid email address");
        }

        return new CreateUserCommand(
                name.trim(),
                email.trim().toLowerCase(),
                password,
                photo.trim(),
                age,
                location.trim(),
                UserRoleName.valueOf(r.roleName().trim().toUpperCase())
        );

    }

    private static String require(String v, String field) {
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException("[CreateUserCommand] " + field + " must not be blank");
        }
        return v;
    }
}
