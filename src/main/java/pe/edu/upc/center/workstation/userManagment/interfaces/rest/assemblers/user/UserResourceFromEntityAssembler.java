package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.UserResponse;

public final class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {
    }

    public static UserResponse toResource(User entity) {
        if (!entity.getEmail().address().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"))
            throw new IllegalArgumentException("[userResourceFromEntity] Invalid email address  ");
        return new UserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail().address(),
                entity.getPhoto(),
                entity.getAge(),
                entity.getLocation(),
                entity.getRegisterDate()
        );
    }
};