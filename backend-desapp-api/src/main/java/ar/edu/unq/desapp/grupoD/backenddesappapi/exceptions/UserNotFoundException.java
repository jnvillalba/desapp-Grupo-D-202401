package ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID: " + id + " not found");
    }

}

