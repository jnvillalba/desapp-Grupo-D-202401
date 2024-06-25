package ar.edu.unq.desapp.grupod.backenddesappapi.security;

import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JWTTokenHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JWTTokenHelperTest {

    private static final String SECRET = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    @InjectMocks
    private JWTTokenHelper jwtTokenHelper;

    @Mock
    private Keys keys;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "testuser";

        String token = generateToken(username);

        assertNotNull(token);
        assertTrue(token.length() > 0);

        assertTrue(jwtTokenHelper.validateToken(token, username));

        String extractedUsername = jwtTokenHelper.getUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    private String generateToken(String userName) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWTTokenHelper.JWT_TOKEN_VALIDITY_MILLIS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
