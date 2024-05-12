package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    @NotNull(message = "The user cannot be null.")
    @ManyToOne
    private User user;

    private OperationType operationType;

    @NotNull(message = "The crypto active cannot be null.")
    @ManyToOne
    private CryptoActive cryptoActive;

    private TransactionStatus status;

    @NotBlank
    private String address;
    private String cvu;


    @DecimalMin(value = "0.0", message = "operation amount cannot be negative.")
    private double operationAmount;

    private LocalDateTime createdAt;


    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        CANCELED_BY_USER,
        CANCELED_BY_SYSTEM
    }

    boolean isSuccess(){
        return status.equals(TransactionStatus.CONFIRMED);
    }

    public boolean isCancelled() {
        return isCancelledByUser() || isCancelledBySystem() ;
    }

    public boolean isPending() {
        return status.equals(TransactionStatus.PENDING);
    }

    public boolean isCancelledByUser(){
        return status == TransactionStatus.CANCELED_BY_USER;
}

    public boolean isCancelledBySystem(){
        return status == TransactionStatus.CANCELED_BY_SYSTEM;
    }

    public boolean wasWithin30Minutes() {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(createdAt, now).toMinutes() <= 30;
    }
}
