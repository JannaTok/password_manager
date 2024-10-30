package passwords.api.password.entity;

import jakarta.persistence.*;
import lombok.*;
import passwords.api.device.entity.Device;

import java.util.UUID;

@Entity
@Table(name = "passwords")
@Getter
@Setter
@NoArgsConstructor
public class Password {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false)
    private Device device;

    public Password(String name, String login, String password) {
        id = UUID.randomUUID();
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
