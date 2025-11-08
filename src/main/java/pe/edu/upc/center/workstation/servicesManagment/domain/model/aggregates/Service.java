package pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "services")
public class Service extends AuditableAbstractAggregateRoot<Service> {

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "spaceId",
                column = @Column(name = "space_id", nullable = false)),
    })
    private SpaceId  spaceId;

    @Getter
    @NotBlank
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Getter
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Getter
    @Min(0)
    @Column(name = "price", nullable = false)
    private Double price;

    protected Service(){}

    public Service(CreateServiceCommand command) {
        this.spaceId = command.spaceId();
        this.name = command.name();
        this.description = command.description();
        this.price = command.price();
    }

    public void updateService(UpdateServiceCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.price = command.price();
    }

}
