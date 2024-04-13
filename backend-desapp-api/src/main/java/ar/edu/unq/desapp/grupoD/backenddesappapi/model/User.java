package ar.edu.unq.desapp.grupoD.backenddesappapi.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    //seguramente no utilizemos el pattern xq se cifraran las contraseñas y ahi se hara la validacion
    // al menos 1 minúscula, 1 mayuscula, 1 carac especial y min 6
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
    private String password;

    @NotBlank
    @Size(min = 22, max = 22)
    private String cvuMercadoPago;

    @NotBlank
    @Size(min = 8, max = 8)
    private String walletCripto;

    //@ManyToMany
    //@JoinColumn(name = "id_role")
    private List<Role> roles = new ArrayList<>();

}