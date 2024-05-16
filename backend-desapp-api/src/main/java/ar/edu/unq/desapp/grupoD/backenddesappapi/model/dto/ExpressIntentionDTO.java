package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpressIntentionDTO {
    private OperationType operationType;

    @NotNull(message = "Crypto active cannot be null.")
    private Long activeId;

    @NotNull(message = "Pesos Amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Pesos Amount cannot be negative.")
    private double pesosAmount;
}
