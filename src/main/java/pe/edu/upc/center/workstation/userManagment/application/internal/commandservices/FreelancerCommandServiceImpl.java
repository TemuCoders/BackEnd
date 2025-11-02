package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.FreelancerRepository;

import java.util.*;

@Service
public class FreelancerCommandServiceImpl implements FreelancerCommandService {

    private final FreelancerRepository repository;

    public FreelancerCommandServiceImpl(FreelancerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long handle(CreateFreelancerCommand command) {
        var freelancer = new Freelancer(command.userType());
        try {
            repository.save(freelancer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving freelancer: " + e.getMessage());
        }
        return freelancer.getId();
    }

    @Override
    public Optional<Freelancer> handle(UpdateFreelancerCommand command) {
        var id = command.freelancerId();
        var agg = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + id + " does not exist"));
        agg.updateUserType(command.userType());
        try {
            return Optional.of(repository.save(agg));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating freelancer: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteFreelancerCommand command) {
        var id = command.freelancerId();
        if (!repository.existsById(id))
            throw new IllegalArgumentException("Freelancer with id " + id + " does not exist");
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting freelancer: " + e.getMessage());
        }
    }

    @Override
    public void handle(AddPreferenceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.addPreference(command.tag());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding preference: " + e.getMessage());
        }
    }

    @Override
    public void handle(RemovePreferenceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.removePreference(command.tag());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while removing preference: " + e.getMessage());
        }
    }

    @Override
    public void handle(RegisterBookingRefCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.registerBookingRef(command.bookingId());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while registering booking: " + e.getMessage());
        }
    }

    @Override
    public void handle(CancelBookingRefCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.cancelBookingRef(command.bookingId());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while canceling booking: " + e.getMessage());
        }
    }

    @Override
    public void handle(AddFavoriteSpaceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.addFavoriteSpace(command.spaceId());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding favorite space: " + e.getMessage());
        }
    }

    @Override
    public void handle(RemoveFavoriteSpaceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new IllegalArgumentException("Freelancer with id " + command.freelancerId() + " does not exist"));
        agg.removeFavoriteSpace(command.spaceId());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while removing favorite space: " + e.getMessage());
        }
    }
}
