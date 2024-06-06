package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.OperationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpressIntentionDTO {

    @NotNull(message = "User cannot be null.")
    private Long userId;

    private OperationType operationType;

    @NotNull(message = "Crypto active cannot be null.")
    private Long activeId;

    @NotNull(message = "Pesos Amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Pesos Amount cannot be negative.")
    private double pesosAmount;
}
