package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import jakarta.validation.constraints.*;

public record RegisterUserCommand(String name, String email, String password, String photo, int age, String location) {
}
