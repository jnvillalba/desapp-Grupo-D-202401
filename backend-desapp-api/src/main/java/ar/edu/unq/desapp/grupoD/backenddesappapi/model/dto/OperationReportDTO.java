package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OperationReportDTO {
    private LocalDateTime requestDateTime;
    private Double totalValueInDollars;
    private Double totalValueInPesosARG;
    private List<ActiveDTO> actives;

}
