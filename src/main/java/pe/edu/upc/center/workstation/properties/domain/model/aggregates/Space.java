package pe.edu.upc.center.workstation.properties.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

public class Space extends AuditableAbstractAggregateRoot<Space> {
}
