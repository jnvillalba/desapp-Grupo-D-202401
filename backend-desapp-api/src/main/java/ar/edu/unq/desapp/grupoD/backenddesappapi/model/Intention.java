package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Intention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long intentionId;

    private LocalDateTime creationDateTime;

    @NotNull(message = "The user cannot be null.")
    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @NotNull(message = "Crypto active cannot be null.")
    @ManyToOne
    private CryptoActive cryptoActive;

    @NotNull(message = "Pesos Amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Pesos Amount cannot be negative.")
    private double pesosAmount;
}

