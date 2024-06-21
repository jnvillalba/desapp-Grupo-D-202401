package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@AllArgsConstructor
public class JwtDTO {
    @JsonProperty("token")
    private String token;

    @JsonProperty("email")
    private String email;

    @JsonProperty("authorities")
    private Collection<? extends GrantedAuthority> authorities;
}



