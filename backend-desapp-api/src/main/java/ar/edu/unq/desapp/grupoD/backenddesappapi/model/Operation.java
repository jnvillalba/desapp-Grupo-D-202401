package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class Operation {

    private int operationId;
    @NotNull(message = "The user cannot be null.")
    private User user;

    private OperationType operationType;

    @NotNull(message = "The crypto active cannot be null.")
    private CryptoActive cryptoActive;

    @NotNull(message = "Cripto amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto amount cannot be negative.")
    private double criptoAmount;

    @NotNull(message = "Cripto price cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto price cannot be negative.")
    private double criptoPrice;

    private Status status;

    public enum Status {
        SUCCESSFUL, FAILED
    }

    boolean isSuccess(){
        return status.equals(Status.SUCCESSFUL);
    }
}
