package passwords.api.user.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import passwords.api.configuration.JwtAuthenticator;
import passwords.api.user.dto.request.AuthenticationRequest;
import passwords.api.user.dto.response.AuthenticationResponse;
import passwords.api.user.entity.Token;
import passwords.api.user.entity.User;
import passwords.api.user.repository.UserCrudRepository;

@Service
@RequiredArgsConstructor
public class SignInHandler {
    private final UserCrudRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticator authenticator;

    public AuthenticationResponse handle(AuthenticationRequest request) throws ResponseStatusException {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Token token = new Token();
        user.setToken(token);
        repository.save(user);

        return new AuthenticationResponse(
            authenticator.generateToken(
                user.getEmail(),
                token
            )
        );
    }
}
