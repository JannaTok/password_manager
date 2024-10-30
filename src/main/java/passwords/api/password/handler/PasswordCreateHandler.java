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
import passwords.api.user.repository.UserCrudRepository;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PasswordCreateHandler {
    private final DeviceCrudRepository deviceCrudRepository;
    private final PasswordCrudRepository passwordCrudRepository;

    public void handle(PasswordCreateRequest request, User user) throws ResponseStatusException {
        Device device = user.getDevices().getFirst();
        Password password = new Password(request.getName(), request.getLogin(), request.getPassword());
        password.setDevice(device);
        passwordCrudRepository.save(password);
        device.addPassword(password);
        deviceCrudRepository.save(device);
    }
}
