package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

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

    @NotBlank(message = "Symbol cannot be null.")
    private String symbol;
    @PositiveOrZero(message = "Price cannot be negative.")
    private Float price;
    private LocalDateTime lastUpdateDateAndTime;
}
