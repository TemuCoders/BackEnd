package pe.edu.upc.center.workstation.propertiesManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByIdQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByNameQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByOwnerQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceQueryService;
import pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories.SpaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceQueryServiceImpl implements SpaceQueryService {
    private final SpaceRepository repository;

    public SpaceQueryServiceImpl(SpaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Space> handle(GetAllSpacesQuery query){
        return repository.findAll();
    }

    @Override
    public Optional<Space> handle(GetSpaceByIdQuery query){
        return  repository.findById(query.spaceId());
    }

    @Override
    public List<Space> handle(GetSpaceByNameQuery query){
        return repository.findByName(query.name());
    }

    @Override
    public  List<Space> handle(GetSpaceByOwnerQuery query){
        return repository.findByOwnerId(query.ownerId());
    }

}
