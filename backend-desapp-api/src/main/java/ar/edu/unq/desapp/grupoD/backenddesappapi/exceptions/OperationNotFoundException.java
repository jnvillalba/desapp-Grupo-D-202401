package ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException(Long id) {
        super("Operation with ID: " + id + " not found");
    }

}

