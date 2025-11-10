package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.FreelancerRepository;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FreelancerQueryServiceImpl implements FreelancerQueryService {

    private final FreelancerRepository repository;

    public FreelancerQueryServiceImpl(FreelancerRepository repository) {
        this.repository = repository;
    }

    private Freelancer loadOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, id));
    }

    @Override
    public List<Freelancer> handle(GetAllFreelancersQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Freelancer> handle(GetFreelancerByIdQuery query) {
        return Optional.of(loadOrThrow(query.freelancerId()));
    }

    @Override
    public List<Long> handle(GetFreelancerFavoriteSpacesQuery query) {
        var agg = loadOrThrow(query.freelancerId());
        return agg.getFavoriteSpaceIds();
    }

    @Override
    public List<String> handle(GetFreelancerPreferencesQuery query) {
        var agg = loadOrThrow(query.freelancerId());
        return agg.getPreferences();
    }

}