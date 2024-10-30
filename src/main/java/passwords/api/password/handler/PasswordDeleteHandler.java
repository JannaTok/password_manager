package passwords.api.password.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.device.entity.Device;
import passwords.api.device.repository.DeviceCrudRepository;
import passwords.api.password.dto.request.PasswordCreateRequest;
import passwords.api.password.entity.Password;
import passwords.api.password.repository.PasswordCrudRepository;
import passwords.api.user.entity.User;

@Service
@RequiredArgsConstructor
public class PasswordDeleteHandler {
    private final DeviceCrudRepository deviceCrudRepository;
    private final PasswordCrudRepository passwordCrudRepository;

    public void handle(String id, User user) throws ResponseStatusException {
        Device device = user.getDevices().getFirst();
        Password passwordToRemove = null;

        for (Password password : device.getPasswords()) {
            if (password.getId().toString().equals(id)) {
                passwordToRemove = password;
            }
        }

        if (passwordToRemove != null) {
            device.removePassword(passwordToRemove);
            deviceCrudRepository.save(device);
            passwordCrudRepository.delete(passwordToRemove);
        }
    }
}
