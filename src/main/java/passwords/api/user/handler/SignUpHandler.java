package passwords.api.user.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.configuration.JwtAuthenticator;
import passwords.api.device.entity.Device;
import passwords.api.device.repository.DeviceCrudRepository;
import passwords.api.user.dto.request.SignUpRequest;
import passwords.api.user.dto.response.AuthenticationResponse;
import passwords.api.user.entity.Token;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

@Service
@RequiredArgsConstructor
public class SignUpHandler {
    private final UserCrudRepository repository;
    private final DeviceCrudRepository deviceCrudRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticator authenticator;

    public AuthenticationResponse handle(SignUpRequest request) throws ResponseStatusException {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        Token token = new Token();
        user.setToken(token);
        repository.save(user);
        Device device = new Device("default", "default", request.getPublicKey());
        device.setUser(user);
        deviceCrudRepository.save(device);
        user.addDevice(device);
        repository.save(user);

        return new AuthenticationResponse(
            authenticator.generateToken(
                user.getEmail(),
                token
            )
        );
    }
}
