package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BinancePriceDTO implements Serializable {
    private String symbol;
    private Float price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime lastUpdateDateAndTime;
}