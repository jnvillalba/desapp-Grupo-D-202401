package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestReportDTO {

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Float dolarBlue;
}
