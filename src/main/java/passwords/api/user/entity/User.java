package passwords.api.user.entity;

import jakarta.persistence.*;
import lombok.*;
import passwords.api.device.entity.Device;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @NonNull
    private String email;

    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token", referencedColumnName = "token")
    @NonNull
    private Token token;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }
}
