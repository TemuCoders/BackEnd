package pe.edu.upc.center.workstation.properties.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "services")
public class Service extends AuditableAbstractAggregateRoot {

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Min(0) @Max(50)
    @Column(name = "price", nullable = false)
    private Double price;

    protected Service() {}

    public Service(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
