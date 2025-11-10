package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService {

    private final OwnerRepository ownerRepository;

    public OwnerCommandServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Long handle(CreateOwnerCommand command) {
        var owner = new Owner(command.company(), command.ruc());
        try {
            ownerRepository.save(owner);
            return owner.getId();
        } catch (Exception e) {
            throw new PersistenceException("[OwnerCommandServiceImpl] Error while saving owner: " + e.getMessage());
        }
    }

    @Override
    public Optional<Owner> handle(UpdateOwnerCommand command) {
        var id = command.ownerId();

        if (!ownerRepository.existsById(id)) {
            throw new NotFoundIdException(Owner.class, id);
        }

        var agg = ownerRepository.findById(id).get();
        // requiere método update en el aggregate (ver snippet más abajo)
        agg.update(command.company(), command.ruc());

        try {
            return Optional.of(ownerRepository.save(agg));
        } catch (Exception e) {
            throw new PersistenceException("[OwnerCommandServiceImpl] Error while updating owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteOwnerCommand command) {
        var id = command.ownerId();
        if (!ownerRepository.existsById(id)) {
            throw new NotFoundIdException(Owner.class, id);
        }
        try {
            ownerRepository.deleteById(id);
        } catch (Exception e) {
            throw new PersistenceException("[OwnerCommandServiceImpl] Error while deleting owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(RegisterSpaceToOwnerCommand command) {
        var agg = ownerRepository.findById(command.ownerId())
                .orElseThrow(() -> new NotFoundIdException(Owner.class, command.ownerId()));
        agg.registerSpace(command.spaceId());
        try {
            ownerRepository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException("[OwnerCommandServiceImpl] Error while registering space to owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(RemoveSpaceFromOwnerCommand command) {
        var agg = ownerRepository.findById(command.ownerId())
                .orElseThrow(() -> new NotFoundIdException(Owner.class, command.ownerId()));
        agg.removeSpace(command.spaceId());
        try {
            ownerRepository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException("[OwnerCommandServiceImpl] Error while removing space from owner: " + e.getMessage());
        }
    }

    @Override
    public void handle(RegisterSpaceForOwnerCommand cmd) {
        handle(new RegisterSpaceToOwnerCommand(cmd.ownerId(), cmd.spaceId()));
    }

    @Override
    public void handle(RemoveSpaceForOwnerCommand cmd) {
        handle(new RemoveSpaceFromOwnerCommand(cmd.ownerId(), cmd.spaceId()));
    }
}
