package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

public record RegisterUserCommand(String name, String email, String password, String photo, int age, String location) {}
