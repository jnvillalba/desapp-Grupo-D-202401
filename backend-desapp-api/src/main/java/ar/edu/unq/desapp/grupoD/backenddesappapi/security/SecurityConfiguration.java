package ar.edu.unq.desapp.grupod.backenddesappapi.security;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Role;
import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JWTTokenFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@Slf4j
@AllArgsConstructor
public class SecurityConfiguration {

    private final JWTTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(antMatcher("/swagger-ui/**"),
                                        antMatcher("/swagger-ui.html"),
                                        antMatcher("/v3/**"),
                                        antMatcher("/h2-console/**"),
                                        antMatcher("/console/**"),
                                        antMatcher("/actuator/prometheus"),
                                        antMatcher("/actuator/**"),
                                        antMatcher("/api/auth/**")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/crypto/intentions")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/crypto/crypto/prices")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/crypto/crypto/**")).permitAll().requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/login/")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/crypto/intention")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/login/")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/crypto/operation/processTransaction")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "api/crypto/intention")).hasRole(Role.USER.name())
                                .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2-console are working
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add your custom filter here

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info(">>> Initilizing Bean AuthenticationManager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        log.info(">>> Initilizing Bean BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

}
