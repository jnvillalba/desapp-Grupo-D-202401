//package ar.edu.unq.desapp.grupoD.backenddesappapi.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//
//    private final String secretKey;
//    private final Long expDate;
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//
//    public JwtTokenProvider(@Value("${app.security.jwt.secret-key}") String secretKey,
//                            @Value("${app.security.expiration-time}") Long expDate) {
//        this.secretKey = secretKey;
//        this.expDate = expDate;
//    }
//
//    public String generateToken(Authentication authentication) {
//        String email = authentication.getName();
//        Date now = new Date();
//        Date expirationToken = new Date(now.getTime() + expDate);
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(expirationToken)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//
//    public String getEmailFromJwt(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    public Boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            logger.error("Exception while validating token");
//        }
//        return false;
//    }
//}