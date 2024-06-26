package ar.edu.unq.desapp.grupod.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CryptoActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeId;

    @NotBlank(message = "Crypto symbol cannot be null.")
    private String symbol;

    @NotNull(message = "Cripto price cannot be null.")
    @PositiveOrZero(message = "Cripto price cannot be negative.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00000000")
    private Float price;

    @NotNull(message = "Cripto amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto amount cannot be negative.")
    private double amount;

    private LocalDateTime lastUpdateDateAndTime;
}