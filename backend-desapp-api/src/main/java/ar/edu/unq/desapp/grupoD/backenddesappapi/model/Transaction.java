package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private Long id;
    private LocalDateTime createdAt;
    private User cancelledBy;
    private User buyer;
    private User seller;

    private CryptoActive criptoActive;
    private OperationType operationType;

    @NotNull(message = "operation amount cannot be null.")
    @DecimalMin(value = "0.0", message = "operation amount cannot be negative.")
    private double operationAmount;

    @NotBlank
    private String address;
    private String cvu;
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }

    public enum CancelledBy {
        USER,
        SYSTEM
    }

    public boolean wasWithin30Minutes() {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(createdAt, now).toMinutes() <= 30;
    }

    public boolean isCancelled() {
        return status == TransactionStatus.CANCELED;
    }

    public boolean isSuccessful() {
        return status == TransactionStatus.CONFIRMED;
    }

    public boolean isCancelledBy(User user){
        return cancelledBy == user;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
