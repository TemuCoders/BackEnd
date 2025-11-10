package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import pe.edu.upc.center.workstation.shared.utils.Util;

import java.util.Objects;


public record CreateOwnerCommand(String company, String ruc) {
    public CreateOwnerCommand {
        if (Objects.isNull(company) || company.isBlank())
            throw new IllegalArgumentException("[CreateOwnerCommand] company must not be blank");
        if (Objects.isNull(ruc) || ruc.isBlank())
            throw new IllegalArgumentException("[CreateOwnerCommand] ruc must not be blank");
        if (!ruc.chars().allMatch(Character::isDigit))
            throw new IllegalArgumentException("[CreateOwnerCommand] ruc must contain only digits");
        if (ruc.length() != Util.RUC_LENGTH)
            throw new IllegalArgumentException("[CreateOwnerCommand] ruc must be "+Util.RUC_LENGTH+" digits");
    }
}
