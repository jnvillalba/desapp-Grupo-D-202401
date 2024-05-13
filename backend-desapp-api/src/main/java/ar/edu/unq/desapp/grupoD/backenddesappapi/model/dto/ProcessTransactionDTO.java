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
        REALIZAR_TRANSFERENCIA("Realize the transfer"),
        CONFIRMAR_RECEPCION("Confirm reception"),
        CANCELAR("Cancel");

        private final String description;
    }

}
