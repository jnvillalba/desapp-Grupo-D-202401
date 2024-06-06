package ar.edu.unq.desapp.grupod.backenddesappapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID: " + id + " not found");
    }

}

