package passwords.api.user.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

@Service
@RequiredArgsConstructor
public class SignOutHandler {
    private final UserCrudRepository repository;

    public void handle(User user) throws ResponseStatusException {
        user.getToken().expire();
        repository.save(user);
    }
}
