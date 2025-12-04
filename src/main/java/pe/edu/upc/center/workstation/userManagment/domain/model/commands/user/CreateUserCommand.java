package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;


public record CreateUserCommand(String name, String email, String password, String photo, int age, String location) {}
