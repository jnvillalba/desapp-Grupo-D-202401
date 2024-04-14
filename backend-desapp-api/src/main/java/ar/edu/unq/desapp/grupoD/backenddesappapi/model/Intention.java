package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Intention {
    private Long intentionId;

    private LocalDateTime creationDateTime;

    @NotNull(message = "The user cannot be null.")
    private User user;

    private OperationType operationType;

    @NotNull(message = "Crypto active cannot be null.")
    private CryptoActive cryptoActive;

    @NotNull(message = "Cripto amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto amount cannot be negative.")
    private double criptoAmount;

    @NotNull(message = "Cripto price cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto price cannot be negative.")
    private double criptoPrice;

    @NotNull(message = "Pesos Amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Pesos Amount cannot be negative.")
    private double pesosAmount;

}

