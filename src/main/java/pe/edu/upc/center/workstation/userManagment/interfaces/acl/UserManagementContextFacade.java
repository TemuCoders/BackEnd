package pe.edu.upc.center.workstation.userManagment.interfaces.acl;


public interface UserManagementContextFacade {


    Long registerUser(String name, String email, String password, String photo, int age, String location,String roleName);

    Long fetchUserIdByEmail(String email);


    boolean existsUserByEmail(String email);
}
