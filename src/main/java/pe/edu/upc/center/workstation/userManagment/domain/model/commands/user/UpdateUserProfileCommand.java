package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

public record UpdateUserProfileCommand(int userId, String name, int age, String location, String photo) {
}
