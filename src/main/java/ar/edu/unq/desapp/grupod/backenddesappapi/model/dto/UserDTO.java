package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

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

    @DecimalMin(value = "0.00", message = "Reputation must be at least 0")
    @DecimalMax(value = "100.00", message = "Reputation must not exceed 100")
    private Double reputation;

    private Integer operationsAmount;

    public User toModel() {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDirection(direction);
        user.setPassword(password);
        user.setCvuMercadoPago(cvuMercadoPago);
        user.setWalletCrypto(walletCrypto);
        return user;
    }

    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setLastName(user.getLastName());
        dto.setDirection(user.getDirection());
        dto.setOperationsAmount(user.getOperationsList().size());
        dto.setReputation(user.findReputation());
        return dto;
    }

}
