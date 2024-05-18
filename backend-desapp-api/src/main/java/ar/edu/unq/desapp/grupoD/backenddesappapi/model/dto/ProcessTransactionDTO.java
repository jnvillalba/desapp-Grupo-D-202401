package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public
class ProcessTransactionDTO {

    private ProcessAccion processType;
    private Long operationId;


    @Getter
    @AllArgsConstructor
    public enum ProcessAccion {
        TRANSFER("Realize the transfer"),
        CONFIRM("Confirm reception"),
        CANCEL("Cancel");

        private final String description;
    }

}
