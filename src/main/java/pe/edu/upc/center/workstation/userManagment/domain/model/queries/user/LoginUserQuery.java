package pe.edu.upc.center.workstation.userManagment.domain.model.queries.user;

import java.util.Objects;

public record LoginUserQuery(String email, String password) {
    public LoginUserQuery {
        Objects.requireNonNull(email, "[LoginUserQuery] email must not be null");
        Objects.requireNonNull(password, "[LoginUserQuery] password must not be null");
        if (email.isBlank()) throw new IllegalArgumentException("[LoginUserQuery] email must not be blank");
        if (password.isBlank()) throw new IllegalArgumentException("[LoginUserQuery] password must not be blank");
    }
}
