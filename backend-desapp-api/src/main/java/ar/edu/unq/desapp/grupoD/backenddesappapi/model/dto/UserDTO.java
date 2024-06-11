package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

}
