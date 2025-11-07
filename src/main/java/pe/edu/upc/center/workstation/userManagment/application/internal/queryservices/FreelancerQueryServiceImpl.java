package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.FreelancerRepository;

import java.util.*;
import java.util.List;

@Service
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
    public List<Long> handle(GetFreelancerBookingsQuery query) {
        var agg = repository.findById(query.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + query.freelancerId() + " does not exist"));
        return agg.getBookedSpaceIds();
    }

    @Override
    public List<Long> handle(GetFreelancerFavoriteSpacesQuery query) {
        var agg = repository.findById(query.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + query.freelancerId() + " does not exist"));
        return agg.getFavoriteSpaceIds();
    }

    @Override
    public List<String> handle(GetFreelancerPreferencesQuery query) {
        var agg = repository.findById(query.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + query.freelancerId() + " does not exist"));
        return agg.getPreferences();
    }

    @Override
    public List<Long> handle(GetFreelancerBookedSpacesQuery q) {
        return List.of();
    }
}