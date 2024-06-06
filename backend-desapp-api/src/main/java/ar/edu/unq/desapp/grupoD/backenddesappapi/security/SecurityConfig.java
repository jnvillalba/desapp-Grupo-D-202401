package ar.edu.unq.desapp.grupod.backenddesappapi.security;

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
        http.authorizeRequests(auth -> auth
                .anyRequest().permitAll());

        return http.csrf().disable().build();
    }


//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(toH2Console())
//                        .disable()
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                        .requestMatchers(toH2Console()).permitAll()
//                )
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .httpBasic(AbstractHttpConfigurer::disable)  // Disables basic authentication
//                .formLogin(AbstractHttpConfigurer::disable); // Disables form login
//
//        return http.build();
//    }

}
