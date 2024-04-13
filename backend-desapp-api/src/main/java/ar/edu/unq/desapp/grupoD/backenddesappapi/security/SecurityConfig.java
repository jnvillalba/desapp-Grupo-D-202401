package ar.edu.unq.desapp.grupoD.backenddesappapi.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Allow access to all URLs without authentication (temporal hasta entrega 2)
        http.authorizeRequests(auth -> auth
                .anyRequest().permitAll());

        return http.csrf().disable().build();
    }

}

