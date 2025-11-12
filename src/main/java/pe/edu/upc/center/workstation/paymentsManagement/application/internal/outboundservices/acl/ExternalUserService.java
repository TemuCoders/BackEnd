package pe.edu.upc.center.workstation.paymentsManagement.application.internal.outboundservices.acl;

import org.springframework.beans.factory.annotation.Qualifier;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.UserId;
import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl.UserAclClient;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;
import org.springframework.stereotype.Service;

@Service("paymentsExternalUserService")
public class ExternalUserService {

    private final UserAclClient userAclClient;

    public ExternalUserService(@Qualifier("paymentsUserAclClient") UserAclClient userAclClient) {
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