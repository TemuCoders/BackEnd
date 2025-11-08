package pe.edu.upc.center.workstation.servicesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceBySpaceIdQuery;

import java.util.Optional;

public interface ServiceCommandService {
    Long handle(CreateServiceCommand command);

    Optional<Service> handle(UpdateServiceCommand command);

    void handle(DeleteServiceCommand command);
}
