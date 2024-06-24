package ar.edu.unq.desapp.grupod.backenddesappapi.exceptions;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException(Long id) {
        super("Operation with ID: " + id + " not found");
    }

}

