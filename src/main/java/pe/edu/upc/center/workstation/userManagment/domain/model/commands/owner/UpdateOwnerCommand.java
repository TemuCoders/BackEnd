package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import pe.edu.upc.center.workstation.shared.utils.Util;

import java.util.Objects;


public record UpdateOwnerCommand(Long ownerId, String company, String ruc) {
    public UpdateOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[UpdateOwnerCommand] ownerId must be > 0");
        if (Objects.isNull(company) || company.isBlank())
            throw new IllegalArgumentException("[UpdateOwnerCommand] company must not be blank");
        if (Objects.isNull(ruc) || ruc.isBlank())
            throw new IllegalArgumentException("[UpdateOwnerCommand] ruc must not be blank");
        if (!ruc.chars().allMatch(Character::isDigit))
            throw new IllegalArgumentException("[UpdateOwnerCommand] ruc must contain only digits");
        if (ruc.length() != Util.RUC_LENGTH)
            throw new IllegalArgumentException("[UpdateOwnerCommand] ruc must be "+Util.RUC_LENGTH+" digits");
    }
}
