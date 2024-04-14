package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Transaction {

    private Long id;

    private CryptoActive criptoActive;
    private OperationType operationType;

    @NotNull(message = "operation amount cannot be null.")
    @DecimalMin(value = "0.0", message = "operation amount cannot be negative.")
    private double operationAmount;

    private User user;

    @NotBlank
    private String address;
    private String cvu;
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }
}
