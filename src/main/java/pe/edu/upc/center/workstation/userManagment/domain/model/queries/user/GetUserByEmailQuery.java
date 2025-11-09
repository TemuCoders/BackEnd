package pe.edu.upc.center.workstation.userManagment.domain.model.queries.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;

public record GetUserByEmailQuery(EmailAddress email) {
}
