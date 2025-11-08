package pe.edu.upc.center.workstation.propertiesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByIdQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByNameQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByOwnerQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling spaces-related queries.
 */
public interface SpaceQueryService {
    /**
     * Handle the query to get al the spaces
     * @param query the query to get all spaces
     * @return a list of all spaces
     */
    List<Space> handle(GetAllSpacesQuery query);

    /**
     * Handle the query to get a spaces by their ID.
     * @param query the query containing the space ID.
     * @return an Optional containing the Space if found, or empty if not found
     */
    Optional<Space> handle(GetSpaceByIdQuery query);

    /**
     * Handle the query to get a space by their name.
     * @param query the query containing the space name.
     * @return an Optinal containing the Space if found, or empty if not found.
     */
    Optional<Space> handle(GetSpaceByNameQuery query);

    /**
     * Handle the query to get a list of spaces by their Owner ID.
     *
     * @param query the query containing the space Owner ID.
     * @return an Optional containing the Spaces if found, or empty if not found.
     */
    List<Space> handle(GetSpaceByOwnerQuery query);
}
