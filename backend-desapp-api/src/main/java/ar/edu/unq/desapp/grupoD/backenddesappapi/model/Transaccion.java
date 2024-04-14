package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Transaccion {

    private Long id;

    private CryptoActive criptoActive;
    private OperationType operationType;

    @NotNull(message = "operation amount cannot be null.")
    @DecimalMin(value = "0.0", message = "operation amount cannot be negative.")
    private double operationAmount;

    private User user;

    @NotBlank
    private String adress;
    private String cvu;
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }
}
