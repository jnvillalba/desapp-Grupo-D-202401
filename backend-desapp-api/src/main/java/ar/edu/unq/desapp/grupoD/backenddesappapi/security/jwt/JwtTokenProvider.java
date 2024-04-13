package ar.edu.unq.desapp.grupoD.backenddesappapi.security.jwt;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final Long exp_date;

    public JwtTokenProvider(@Value("${app.security.jwt.secret-key}") String secretKey,
                            @Value("${app.security.expiration-time}") Long exp_date){
        this.secretKey = secretKey;
        this.exp_date = exp_date;
    }

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date now = new Date();
        Date expirationToken = new Date(now.getTime() + exp_date);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationToken)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public String getEmailFromJwt (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {}
        return false;
    }
}