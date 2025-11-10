package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners;

import pe.edu.upc.center.workstation.shared.utils.Util;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record UpdateOwnerRequest(
        @JsonProperty("company") @NotBlank String company,
        @JsonProperty("ruc")
        @NotBlank
        @Size(min = Util.RUC_LENGTH, max = Util.RUC_LENGTH)
        @Pattern(regexp = "\\d{11}", message = "RUC must be 11 digits")
        String ruc
) {}
