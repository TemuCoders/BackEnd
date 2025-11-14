package pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

/**
 * Query to get by ownerID
 */
public record GetSpaceByOwnerQuery(OwnerId ownerId) {
}
