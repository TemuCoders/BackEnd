package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners.RegisterSpaceForOwnerCommandFromResourceAssembler;

import java.util.Optional;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService{

    private final OwnerRepository ownerRepository;

    public OwnerCommandServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Long handle(CreateOwnerCommand command) {
        var owner = new Owner(command.company(), command.ruc());
        try {
            ownerRepository.save(owner);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving owner: " + e.getMessage());
        }
        return owner.getId();
    }

    @Override
    public Optional<Owner> handle(UpdateOwnerCommand command) {
        var ownerId = command.ownerId();

        if (!ownerRepository.existsById(ownerId)) throw new IllegalArgumentException("Owner with id " + ownerId + " does not exist");

        var ownerToUpdate = ownerRepository.findById(ownerId).get();

        try {
            var updated = ownerRepository.save(ownerToUpdate);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteOwnerCommand command) {
        var ownerId = command.ownerId();

        if (!ownerRepository.existsById(ownerId))
            throw new IllegalArgumentException("Owner with id " + ownerId + " does not exist");

        try {
            ownerRepository.deleteById(ownerId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(RegisterSpaceToOwnerCommand command) {
        var owner = ownerRepository.findById(command.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner with id " + command.ownerId() + " does not exist"));
        owner.registerSpace(command.spaceId());
        try {
            ownerRepository.save(owner);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while registering space to owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(RemoveSpaceFromOwnerCommand command) {
        var owner = ownerRepository.findById(command.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner with id " + command.ownerId() + " does not exist"));
        owner.removeSpace(command.spaceId());
        try {
            ownerRepository.save(owner);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while removing space from owner: " + e.getMessage());
        }
    }


    @Override
    public void handle(RegisterSpaceForOwnerCommand cmd) {
        var owner = ownerRepository.findById(cmd.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + cmd.ownerId()));
        owner.registerSpace(cmd.spaceId());
        ownerRepository.save(owner);
    }

    @Override
    public void handle(RemoveSpaceForOwnerCommand cmd) {
        var owner = ownerRepository.findById(cmd.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + cmd.ownerId()));
        owner.removeSpace(cmd.spaceId());
        ownerRepository.save(owner);
    }
}
