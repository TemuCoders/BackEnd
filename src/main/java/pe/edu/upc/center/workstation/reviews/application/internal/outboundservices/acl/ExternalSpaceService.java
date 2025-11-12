package pe.edu.upc.center.workstation.reviews.application.internal.outboundservices.acl;

import pe.edu.upc.center.workstation.reviews.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.reviews.infrastructure.acl.SpaceAclClient;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundArgumentException;
import org.springframework.stereotype.Service;

@Service
public class ExternalSpaceService {

    private final SpaceAclClient spaceAclClient;

    public ExternalSpaceService(SpaceAclClient spaceAclClient) {
        this.spaceAclClient = spaceAclClient;
    }

    public void validateSpaceExists(SpaceId spaceId) {
        if (!spaceAclClient.existsSpace(spaceId.spaceId())) {
            throw new NotFoundArgumentException("Space not found with id: " + spaceId.spaceId());
        }
    }

    public void validateSpaceExists(Long spaceId) {
        validateSpaceExists(new SpaceId(spaceId));
    }
}