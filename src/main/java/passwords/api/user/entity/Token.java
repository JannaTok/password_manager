package passwords.api.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_tokens")
@Getter
@Setter
public class Token {
    @Id
    @Column(name = "token", length = 64, nullable = false)
    @Setter(AccessLevel.NONE)
    private String token;

    @Column(name = "expires_at", nullable = false)
    @NonNull
    private LocalDateTime expiresAt;

    public Token() {
        this(LocalDateTime.now().plusHours(1));
    }

    public Token(LocalDateTime e) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(64);
        for(int i = 0; i < 64; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        token = sb.toString();
        expiresAt = e;
    }

    public void expire() {
        expiresAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
