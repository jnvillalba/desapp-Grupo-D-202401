package ar.edu.unq.desapp.grupod.backenddesappapi.security;
import java.io.IOException;

import ar.edu.unq.desapp.grupod.backenddesappapi.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JWTTokenFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JWTTokenFilter.class);

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    private String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("validation of JWT token by OncePerRequestFilter");

        String token = getTokenFromRequest(request);

        logger.info("JWT token : " + token);
        String userName = null;

        if (token != null) {

            userName = this.jwtTokenHelper.getUsernameFromToken(token);
            logger.info("JWT token USer NAme : " + userName);
        } else {
            logger.info("ToKen is Misisng. Please Come with Token");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // fetch user detail from username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            Boolean isValidToken = this.jwtTokenHelper.validateToken(token, userDetails.getUsername());
            if (isValidToken) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }

}