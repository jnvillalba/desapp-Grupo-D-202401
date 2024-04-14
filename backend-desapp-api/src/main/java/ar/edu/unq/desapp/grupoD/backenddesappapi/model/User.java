package ar.edu.unq.desapp.grupoD.backenddesappapi.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Size(min = 22, max = 22)
    private String cvuMercadoPago;

    @NotBlank
    @Size(min = 8, max = 8)
    private String walletCrypto;

    //@ManyToMany
    //@JoinColumn(name = "id_role")
    private List<Role> roles = new ArrayList<>();

    private List<Intention> intentionsList = new ArrayList<>();
    private List<Operation> operationsList = new ArrayList<>();


    @DecimalMin(value = "0", inclusive = true, message = "Reputation must be at least 0")
    @DecimalMax(value = "100", inclusive = true, message = "Reputation must not exceed 100")
    private double reputation = 0;

    public double getReputation() {
        calculateReputation();
        return reputation;
    }
    private void calculateReputation() {
        if (!operationsList.isEmpty()) {
            double successfulOperations = countSuccessfulOperations();
            double totalOperations = operationsList.size();
            double successRate = ( totalOperations / successfulOperations) * 100;
            reputation = Math.min(0, Math.max(100, successRate));
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