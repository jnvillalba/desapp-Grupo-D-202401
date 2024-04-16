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
    @Size(min = 22, max = 22, message = "CVU must have 22 digits.")
    private String cvuMercadoPago;

    @NotBlank
    @Size(min = 8, max = 8, message = "Wallet must have 8 digits.")
    private String walletCrypto;

    private List<Role> roles = new ArrayList<>();

    private List<Intention> intentionsList = new ArrayList<>();
    private List<Operation> operationsList = new ArrayList<>();
    // Estamos utilizando tanto Operation y Transaction de la misma manera, quizas
    // tiene sentido unificarlos. En el enunciado noto que lo usan por igual ambos terminos
    private List<Transaction> transactionsList = new ArrayList<>();

    @DecimalMin(value = "0.00", inclusive = true, message = "Reputation must be at least 0")
    @DecimalMax(value = "100.00", inclusive = true, message = "Reputation must not exceed 100")
    private double reputation = 0.0;

    // El problema que tengo con este metodo es que cuando quiero actualizar la reputation
    // cuando se cancela o se completa, este metodo lo pisa y no lo tiene en cuenta
    public double getReputation() {
        calculateReputation();
        return reputation;
    }

    public double findReputation(){
        return reputation;
    }

    // No tiene en cuentatu reputation inicial... si tu reputation es 30 lo pisas y lo calculas de 0
    private void calculateReputation() {
        if (transactionsList.isEmpty()) {
            reputation = 0.0;
            return;
        }

        double successfulTransactions = countSuccessfulTransactions();
        double totalTransactions = transactionsList.size();

        if (successfulTransactions == 0) {
            reputation = 0.0;
        } else {
            double successRate = (successfulTransactions / totalTransactions) * 100;
            reputation = Math.min(100.0, Math.max(0.0, successRate));
        }
    }

    private double countSuccessfulTransactions() {
        double successfulTransactions = 0;
        for (Transaction transaction : transactionsList) {
            if (transaction.isSuccessful()) {
                successfulTransactions++;
            }
        }
        return successfulTransactions;
    }

    // TODO: este metodo se va a llamar a cada user de la transacción una vez realizada la capa de servicio
    public void processTransaction(Transaction transaction) {

        if (transaction.isCancelled()) {
            handleCancelledTransaction(transaction);
        } else if (transaction.isSuccessful()) {
            handleSuccesfulTransaction(transaction);
        }
    }

    public void handleCancelledTransaction(Transaction transaction) {
        if (transaction.isCancelledBy(this)) {
            this.calculateReputation();
            reputation = Math.max(0, (reputation - 20.0));
        }
    }

    public void handleSuccesfulTransaction(Transaction transaction) {
        double reputationPoints = transaction.wasWithin30Minutes() ? 10.0 : 5.0;
        double newReputation = reputation + reputationPoints;

        reputation = Math.max(0, Math.min(100, newReputation));

        transactionsList.add(transaction);
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }
}