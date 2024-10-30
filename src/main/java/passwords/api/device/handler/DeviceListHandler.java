package passwords.api.device.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.device.entity.Device;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DeviceListHandler {
    private final UserCrudRepository repository;

    public ArrayList<Object> handle(User user) throws ResponseStatusException {
        ArrayList<Object> response = new ArrayList<>();

        for (Device device : user.getDevices()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", device.getId().toString());
            map.put("name", device.getName());
            map.put("public_key", device.getPublicKey());
            response.add(map);
        }

        return response;
    }
}
