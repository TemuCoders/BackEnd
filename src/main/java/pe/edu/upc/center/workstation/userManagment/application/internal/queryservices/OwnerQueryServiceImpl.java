package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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
    public Optional<Owner> handle(GetOwnerByUserIdQuery query) {
        return repository.findByUserId(query.userId());
    }

    @Override
    public boolean handle(ExistsOwnerByIdQuery query) {
        return repository.existsById(query.ownerId());
    }

    @Override
    public boolean handle(ExistsOwnerByUserIdQuery query) {
        return repository.existsByUserId(query.userId());
    }
}
