package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;


import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.FreelancerCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.FreelancerRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FreelancerCommandServiceImpl implements FreelancerCommandService {

    private final FreelancerRepository repository;

    public FreelancerCommandServiceImpl(FreelancerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Long handle(CreateFreelancerCommand command) {
        if (repository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Freelancer already exists for userId=" + command.userId());
        }
        var agg = new Freelancer(command.userId(), command.userType());
        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while saving freelancer: " + e.getMessage());
        }
        return agg.getId();
    }

    @Override
    @Transactional
    public Optional<Freelancer> handle(UpdateFreelancerCommand command) {
        var id = command.freelancerId();
        var agg = repository.findById(id)
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, id));

        agg.updateUserType(command.userType());

        try {
            return Optional.of(repository.save(agg));
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while updating freelancer: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(DeleteFreelancerCommand command) {
        var id = command.freelancerId();
        if (!repository.existsById(id))
            throw new NotFoundIdException(Freelancer.class, id);

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while deleting freelancer: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(AddPreferenceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, command.freelancerId()));

        agg.addPreference(command.tag());

        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while adding preference: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(RemovePreferenceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, command.freelancerId()));

        agg.removePreference(command.tag());

        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while removing preference: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(AddFavoriteSpaceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, command.freelancerId()));

        agg.addFavoriteSpace(command.spaceId());

        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while adding favorite space: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(RemoveFavoriteSpaceCommand command) {
        var agg = repository.findById(command.freelancerId())
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, command.freelancerId()));

        agg.removeFavoriteSpace(command.spaceId());

        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while removing favorite space: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(UpdateFreelancerUserTypeCommand c) {
        var agg = repository.findById(c.freelancerId())
                .orElseThrow(() -> new NotFoundIdException(Freelancer.class, c.freelancerId()));

        agg.updateUserType(c.userType());

        try {
            repository.save(agg);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[FreelancerCommandServiceImpl] Error while updating user type: " + e.getMessage());
        }
    }
}
