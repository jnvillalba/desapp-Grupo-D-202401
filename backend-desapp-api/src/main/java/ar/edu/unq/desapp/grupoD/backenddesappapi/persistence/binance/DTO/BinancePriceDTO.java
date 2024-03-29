package ar.edu.unq.desapp.grupoD.backenddesappapi.persistence.binance.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BinancePriceDTO {
    private String symbol;
    private Float price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:")
    private LocalDateTime lastUpdateDateAndTime;
}
