package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

import java.util.Date;

public record UserResource(int id, String name, String email, String photo, int age, String location, Date registerDate
) {}
