package passwords.api.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserCrudRepository userRepository;

    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
