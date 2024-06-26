package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Getter
public class OperationReportDTO {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime requestDateTime;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.00")
    private Double totalValueInDollars;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.00")
    private Double totalValueInPesosARG;
    private List<ActiveDTO> actives;

}
