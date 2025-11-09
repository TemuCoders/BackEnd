package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final OwnerRepository ownerRepository;

    public OwnerQueryServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    private Owner loadOrThrow(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException(Owner.class, id));
    }

    @Override
    public List<Owner> handle(GetAllOwnersQuery query) {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> handle(GetOwnerByIdQuery query) {
        return Optional.of(loadOrThrow(query.ownerId()));
    }

    @Override
    public Optional<Owner> handle(GetOwnerByRucQuery query) {
        return ownerRepository.findByRuc(query.ruc());
    }
}
