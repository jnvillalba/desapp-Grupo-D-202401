package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestReportDTO {

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Float dolarBlue;
}
