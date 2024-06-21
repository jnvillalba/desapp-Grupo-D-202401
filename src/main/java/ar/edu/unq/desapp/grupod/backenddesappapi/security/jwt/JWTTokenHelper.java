package ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@SuppressWarnings("all")
public class JWTTokenHelper {

    public static final long JWT_TOKEN_VALIDITY_MILLIS = 60L * 60000; // 5 mins
    private static final String SECRET = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public static String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    //check if the token has expired
    public static Boolean isTokenExpired(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Date expiration = claimsJws.getBody().getExpiration();
        return expiration != null && expiration.before(new Date());
    }

    //generate token for user
    //Sign the JWT using the HS512 algorithm and secret key.
    public String generateToken(String userName) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLIS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    //validate token
    public Boolean validateToken(String token, String userName) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }

}