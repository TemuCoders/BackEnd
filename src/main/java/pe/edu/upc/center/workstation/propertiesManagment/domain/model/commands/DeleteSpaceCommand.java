package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

/**
 * Command to delete a space by its ID.
 *
 * @param spaceId the ID of the space to be deleted.
 */
public record DeleteSpaceCommand(Long spaceId) {
}
