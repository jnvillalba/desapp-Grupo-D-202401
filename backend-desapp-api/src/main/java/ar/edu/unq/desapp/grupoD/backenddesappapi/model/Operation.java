package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Operation {

    private int operationId;

    @NotNull(message = "The user cannot be null.")
    private User user;

    private OperationType operationType;

    @NotNull(message = "The crypto active cannot be null.")
    private CryptoActive cryptoActive;

    private Status status;

    public enum Status {
        SUCCESSFUL, FAILED
    }

    boolean isSuccess(){
        return status.equals(Status.SUCCESSFUL);
    }
}
