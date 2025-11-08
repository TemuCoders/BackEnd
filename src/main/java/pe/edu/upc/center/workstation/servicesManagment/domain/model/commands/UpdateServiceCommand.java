package pe.edu.upc.center.workstation.servicesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;

import java.util.Objects;

public record UpdateServiceCommand(Long serviceId, String name, String description, Double price) {
   public  UpdateServiceCommand{
       Objects.requireNonNull(name, "[UpdateServiceCommand] name cannot be null");
       Objects.requireNonNull(description, "[UpdateServiceCommand] description cannot be null");
       Objects.requireNonNull(price, "[UpdateServiceCommand] price cannot be null");

       if (serviceId < 0) throw new IllegalArgumentException("[UpdateServiceCommand] serviceId cannot be less than 0");
   }
}
