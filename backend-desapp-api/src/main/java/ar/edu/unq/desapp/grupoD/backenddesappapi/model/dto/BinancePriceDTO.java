package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BinancePriceDTO {
    private String symbol;
    private Float price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime lastUpdateDateAndTime;
}