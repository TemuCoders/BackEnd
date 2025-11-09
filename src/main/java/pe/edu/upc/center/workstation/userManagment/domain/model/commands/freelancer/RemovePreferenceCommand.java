package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record RemovePreferenceCommand(Long freelancerId, String tag) {
    public RemovePreferenceCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[RemovePreferenceCommand] freelancerId must be > 0");
        if (Objects.isNull(tag) || tag.isBlank())
            throw new IllegalArgumentException("[RemovePreferenceCommand] tag must not be blank");
    }
}
