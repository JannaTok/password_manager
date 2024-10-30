package passwords.api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import passwords.api.user.entity.Token;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtAuthenticator {
    private final String privateKeyPath;
    private final long expiration;
    private SecretKey privateKey;

    public JwtAuthenticator(
        @Value("${jwt.secret_key_path}") String privateKeyPath,
        @Value("${jwt.expiration_time}") long expiration
    ) {
        this.privateKeyPath = privateKeyPath;
        this.expiration = expiration;

        File privateKeyFile = new File(this.privateKeyPath);
        if (privateKeyFile.isFile()) {
            try {
                this.privateKey = new SecretKeySpec(
                    Base64.getDecoder().decode(FileUtils.readFileToString(privateKeyFile, "UTF-8")),
                    "HmacSHA512"
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                this.privateKey = Jwts.SIG.HS512.key().build();
                FileUtils.writeStringToFile(
                    privateKeyFile,
                    Base64.getEncoder().encodeToString(this.privateKey.getEncoded()),
                    "UTF-8"
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractToken(String token) {
        return extractClaims(token).get("token", String.class);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(this.privateKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String generateToken(String username, Token token) {
        return Jwts.builder()
            .issuer("passwords.api")
            .subject(username)
            .claim("token", token.getToken())
            .issuedAt(new Date())
            .expiration(Date.from(token.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant())) // Expire when User token expires; there is no reason to keep the JWT available.
            .signWith(this.privateKey)
            .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }
}
