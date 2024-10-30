package passwords.api.password.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.device.entity.Device;
import passwords.api.password.dto.request.PasswordUpdateRequest;
import passwords.api.password.entity.Password;
import passwords.api.password.repository.PasswordCrudRepository;
import passwords.api.user.entity.User;

@Service
@RequiredArgsConstructor
public class PasswordUpdateHandler {
    private final PasswordCrudRepository passwordCrudRepository;

    public void handle(PasswordUpdateRequest request, User user) throws ResponseStatusException {
        Device device = user.getDevices().getFirst();
        for (Password password : device.getPasswords()) {
            if (password.getId().toString().equals(request.getId())) {
                password.setName(request.getName());
                password.setLogin(request.getLogin());
                password.setPassword(request.getPassword());
                passwordCrudRepository.save(password);
            }
        }
    }
}
