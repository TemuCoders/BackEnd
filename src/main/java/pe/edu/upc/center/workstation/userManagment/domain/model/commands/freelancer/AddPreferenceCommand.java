package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record AddPreferenceCommand(Long freelancerId, String tag) {
    public AddPreferenceCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[AddPreferenceCommand] freelancerId must be > 0");
        if (Objects.isNull(tag) || tag.isBlank())
            throw new IllegalArgumentException("[AddPreferenceCommand] tag must not be blank");
        if (tag.trim().length() > 50)
            throw new IllegalArgumentException("[AddPreferenceCommand] tag max length is 50");
    }
}
