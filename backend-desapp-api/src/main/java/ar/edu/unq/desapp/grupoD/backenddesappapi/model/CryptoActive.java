package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoActive {

    @NotBlank(message = "Crypto symbol cannot be null.")
    private String symbol;

    @NotNull(message = "Cripto price cannot be null.")
    @PositiveOrZero(message = "Cripto price cannot be negative.")
    private Float price;

    @NotNull(message = "Cripto amount cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto amount cannot be negative.")
    private double amount;

    @NotNull(message = "Cripto price cannot be null.")
    @DecimalMin(value = "0.0", message = "Cripto quantity cannot be negative.")
    private double quantity;

    private LocalDateTime lastUpdateDateAndTime;
}
