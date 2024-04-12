package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CriptoActive {
    private String symbol;
    private Float price;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime lastUpdateDateAndTime;
}
