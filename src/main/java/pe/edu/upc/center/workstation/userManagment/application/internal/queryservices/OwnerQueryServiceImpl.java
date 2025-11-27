package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.ExistsOwnerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetAllOwnersQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByRucQuery;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final OwnerRepository repository;

    public OwnerQueryServiceImpl(OwnerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Owner> handle(GetAllOwnersQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Owner> handle(GetOwnerByIdQuery query) {
        return repository.findById(query.ownerId());
    }

    @Override
    public Optional<Owner> handle(GetOwnerByRucQuery query) {
        return repository.findByRuc(query.ruc());
    }

    @Override
    public boolean handle(ExistsOwnerByIdQuery query) {
        return repository.existsById(query.ownerId());
    }
}
