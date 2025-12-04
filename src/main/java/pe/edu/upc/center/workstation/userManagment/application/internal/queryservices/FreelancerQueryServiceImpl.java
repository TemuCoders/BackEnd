package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;

import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.FreelancerRepository;

@Service
@Transactional(readOnly = true)
public class FreelancerQueryServiceImpl implements FreelancerQueryService {

    private final FreelancerRepository repository;

    public FreelancerQueryServiceImpl(FreelancerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Freelancer> handle(GetAllFreelancersQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Freelancer> handle(GetFreelancerByIdQuery query) {
        return repository.findById(query.freelancerId());
    }

    @Override
    public List<Long> handle(GetFreelancerFavoriteSpacesQuery query) {
        return repository.findById(query.freelancerId())
                .map(Freelancer::getFavoriteSpaceIds)
                .map(java.util.ArrayList::new)
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, query.freelancerId()));
    }

    @Override
    public List<String> handle(GetFreelancerPreferencesQuery query) {
        return repository.findById(query.freelancerId())
                .map(Freelancer::getPreferences)
                .map(java.util.ArrayList::new)
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, query.freelancerId()));
    }

    @Override
    public Optional<Freelancer> handle(GetFreelancerByUserIdQuery query) {
        return repository.findByUserId(query.userId());
    }

    @Override
    public boolean handle(ExistsFreelancerByIdQuery query) {
        return repository.existsById(query.freelancerId());
    }

    @Override
    public boolean handle(ExistsFreelancerByUserIdQuery query) {
        return repository.existsByUserId(query.userId());
    }
}
