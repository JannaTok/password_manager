package passwords.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import passwords.api.configuration.MutableHttpServletRequest;
import passwords.api.user.dto.request.AuthenticationRequest;
import passwords.api.user.dto.request.SignUpRequest;
import passwords.api.user.dto.response.AuthenticationResponse;
import passwords.api.user.dto.response.EmptyResponse;
import passwords.api.user.handler.SignInHandler;
import passwords.api.user.handler.SignOutHandler;
import passwords.api.user.handler.SignUpHandler;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final SignUpHandler signUpHandler;
    private final SignInHandler signInHandler;
    private final SignOutHandler signOutHandler;

    @PostMapping(
        name = "user.signup",
        value = "/signup",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthenticationResponse> userSignUp(
        @Valid @RequestBody SignUpRequest request
    ) {
        return new ResponseEntity<>(signUpHandler.handle(request), HttpStatus.OK);
    }

    @PostMapping(
        name = "user.signin",
        value = "/signin",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthenticationResponse> userSignIn(
        @Valid @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(signInHandler.handle(request), HttpStatus.OK);
    }

    @GetMapping(
        name = "user.signout",
        value = "/signout",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmptyResponse> userSignOut(
        MutableHttpServletRequest request
    ) {
        signOutHandler.handle(request.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


