package ar.edu.unq.desapp.grupoD.backenddesappapi.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 30)
    private String direction;
    
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
    private String password;

    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must have 22 digits.")
    private String cvuMercadoPago;

    @NotBlank
    @Size(min = 8, max = 8, message = "Wallet must have 8 digits.")
    private String walletCrypto;

    private List<Role> roles = new ArrayList<>();

    private List<Intention> intentionsList = new ArrayList<>();
    private List<Operation> operationsList = new ArrayList<>();

    @DecimalMin(value = "0.00", inclusive = true, message = "Reputation must be at least 0")
    @DecimalMax(value = "100.00", inclusive = true, message = "Reputation must not exceed 100")
    private double reputation = 0.0;

    public double getReputation() {
        calculateReputation();
        return reputation;
    }

    private void calculateReputation() {
        if (operationsList.isEmpty()) {
            reputation = 0.0;
            return;
        }

        double successfulOperations = countSuccessfulOperations();
        double totalOperations = operationsList.size();

        if (successfulOperations == 0) {
            reputation = 0.0;
        } else {
            double successRate = (successfulOperations / totalOperations) * 100;
            reputation = Math.min(100.0, Math.max(0.0, successRate));
        }
    }

    private double countSuccessfulOperations() {
        double successfulOperations = 0;
        for (Operation operation : operationsList) {
            if (operation.isSuccess()) {
                successfulOperations++;
            }
        }
        return successfulOperations;
    }

}