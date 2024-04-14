package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Intention {
    private Long intentionId;

    private LocalDateTime creationDateTime;

    @NotNull(message = "The user cannot be null.")
    private User user;

    private OperationType operationType;

    @NotNull(message = "Crypto active cannot be null.")
    private CryptoActive cryptoActive;

    @NotNull(message = "Pesos Amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Pesos Amount cannot be negative.")
    private double pesosAmount;

}

