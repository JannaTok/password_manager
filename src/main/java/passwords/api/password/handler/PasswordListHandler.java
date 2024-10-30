package passwords.api.password.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.device.entity.Device;
import passwords.api.password.entity.Password;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PasswordListHandler {
    private final UserCrudRepository repository;

    public ArrayList<Object> handle(User user) throws ResponseStatusException {
        Device device = user.getDevices().getFirst();

        ArrayList<Object> response = new ArrayList<>();

        for (Password password : device.getPasswords()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", password.getId().toString());
            map.put("name", password.getName());
            map.put("login", password.getLogin());
            map.put("password", password.getPassword());
            response.add(map);
        }

        return response;
    }
}
