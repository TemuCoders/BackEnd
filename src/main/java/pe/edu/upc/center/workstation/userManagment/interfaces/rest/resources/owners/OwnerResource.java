package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners;

import java.util.List;

public record OwnerResource(Long id, String company, String ruc, List<Long> registeredSpaceIds
) {}
