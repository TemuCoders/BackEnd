package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;


import java.util.*;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService{

    private final OwnerRepository ownerRepository;

    public OwnerQueryServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> handle(GetAllOwnersQuery query) {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> handle(GetOwnerByIdQuery query) {
        return ownerRepository.findById(query.ownerId());
    }

    @Override
    public Optional<Owner> handle(GetOwnerByRucQuery query) {

        return ownerRepository.findByRuc(query.ruc());
    }

    @Override
    public List<Long> handle(GetOwnerRegisteredSpacesQuery query) {
        var owner = ownerRepository.findById(query.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner with id " + query.ownerId() + " does not exist"));
        return owner.getRegisteredSpaceIds();
    }
}
