package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

public record CreateUserResource(String name, String email, String password, String photo, int age, String location
) {

}
