package pe.edu.upc.center.workstation.servicesManagment.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceCommandService;
import pe.edu.upc.center.workstation.servicesManagment.infraestructure.persistense.jpa.repositories.ServiceRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceCommandServiceImpl implements ServiceCommandService {
    private ServiceRepository serviceRepository;
    public ServiceCommandServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Long handle(CreateServiceCommand command){
        var service = new Service(command);

        try{
            var createdService = this.serviceRepository.save(service);
            return createdService.getId();
        } catch (Exception e){
            throw new PersistenceException("[ServiceCommandServiceImpl] Error while creating service" + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteServiceCommand command){
        if(!this.serviceRepository.existsById(command.serviceId())){
            throw new NotFoundIdException(Service.class,command.serviceId());
        }

        try{
            serviceRepository.deleteById(command.serviceId());
        } catch (Exception e){
            throw new IllegalArgumentException("[ServiceCommandServiceImpl] Error while deleting service" + e.getMessage());
        }
    }

    @Override
    public Optional<Service> handle(UpdateServiceCommand command){
        var serviceId = command.serviceId();
        if(!this.serviceRepository.existsById(serviceId)){
            throw new IllegalArgumentException("[ServiceCommandServiceImpl] Error while updating service" + command.serviceId());
        }

        var serviceToUpdate = serviceRepository.findById(serviceId).get();
        serviceToUpdate.updateService(command);

        try{
            var updatedService = this.serviceRepository.save(serviceToUpdate);
            return Optional.of(updatedService);
        } catch (Exception e){
            throw new PersistenceException("[ServiceCommandServiceImpl] Error while updating service" + e.getMessage());
        }
    }
}
