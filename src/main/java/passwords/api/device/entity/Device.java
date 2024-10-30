package passwords.api.device.entity;

import jakarta.persistence.*;
import lombok.*;
import passwords.api.password.entity.Password;
import passwords.api.user.entity.User;

import java.util.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "identity", nullable = false)
    @NonNull
    private String identity;

    @Column(name = "public_key", length = 8200, nullable = false)
    @NonNull
    private String publicKey;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device")
    private List<Password> passwords = new ArrayList<>();

    public Device(String name, String identity, String publicKey) {
        id = UUID.randomUUID();
        this.name = name;
        this.identity = identity;
        this.publicKey = publicKey;
    }

    public void addPassword(Password password) {
        passwords.add(password);
    }

    public void removePassword(Password password) {
        passwords.remove(password);
    }
}
