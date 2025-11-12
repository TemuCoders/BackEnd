package pe.edu.upc.center.workstation.reviews.application.internal.outboundservices.acl;

import pe.edu.upc.center.workstation.reviews.domain.model.valueobjects.UserId;
import pe.edu.upc.center.workstation.reviews.infrastructure.acl.UserAclClient;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;
import org.springframework.stereotype.Service;

@Service
public class ExternalUserService {

    private final UserAclClient userAclClient;

    public ExternalUserService(UserAclClient userAclClient) {
        this.userAclClient = userAclClient;
    }

    public void validateUserExists(UserId userId) {
        if (!userAclClient.existsUser(userId.userId())) {
            throw new NotFoundArgumentException("User not found with id: " + userId.userId());
        }
    }

    public void validateUserExists(Long userId) {
        validateUserExists(new UserId(userId));
    }
}