package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Operation {

    private int operationId;

    @NotNull(message = "The user cannot be null.")
    private User user;

    private OperationType operationType;

    @NotNull(message = "The crypto active cannot be null.")
    private CryptoActive cryptoActive;

    private TransactionStatus status;

    @NotBlank
    private String address;
    private String cvu;

    @NotNull(message = "operation amount cannot be null.")
    @DecimalMin(value = "0.0", message = "operation amount cannot be negative.")
    private double operationAmount;


    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }

    boolean isSuccess(){
        return status.equals(TransactionStatus.CONFIRMED);
    }
}
