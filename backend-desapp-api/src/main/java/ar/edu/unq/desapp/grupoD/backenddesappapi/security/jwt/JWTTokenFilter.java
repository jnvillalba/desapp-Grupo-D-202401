package ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt;
import java.io.IOException;

import ar.edu.unq.desapp.grupod.backenddesappapi.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
@Slf4j
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JWTTokenHelper jwtTokenHelper;

    private final CustomUserDetailsService customUserDetailsService;

    private String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        String userName = null;

        if (token != null) {
            userName = JWTTokenHelper.getUsernameFromToken(token);
            String formattedMessage = "\u001B[32m>>> JWT token User : " + userName + "\u001B[0m";
            logger.info(formattedMessage);
        } else {
            String formattedMessage = "\u001B[31m>>> Token is Missing.\u001B[0m";
            logger.info(formattedMessage);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            Boolean isValidToken = jwtTokenHelper.validateToken(token, userDetails.getUsername());
            if (Boolean.TRUE.equals(isValidToken)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }


}