package passwords.api.password.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import passwords.api.configuration.MutableHttpServletRequest;
import passwords.api.password.dto.request.PasswordCreateRequest;
import passwords.api.password.dto.request.PasswordUpdateRequest;
import passwords.api.password.handler.PasswordCreateHandler;
import passwords.api.password.handler.PasswordDeleteHandler;
import passwords.api.password.handler.PasswordListHandler;
import passwords.api.password.handler.PasswordUpdateHandler;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PasswordController {
    private final PasswordListHandler passwordListHandler;
    private final PasswordCreateHandler passwordCreateHandler;
    private final PasswordUpdateHandler passwordUpdateHandler;
    private final PasswordDeleteHandler passwordDeleteHandler;

    @GetMapping(
        name = "passwords.list",
        value = "/passwords",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArrayList<Object>> passwordsList(
        MutableHttpServletRequest request
    ) {
        return new ResponseEntity<>(passwordListHandler.handle(request.getUser()), HttpStatus.OK);
    }

    @PostMapping(
        name = "passwords.add",
        value = "/passwords",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> passwordsCreate(
        @Valid @RequestBody PasswordCreateRequest request,
        MutableHttpServletRequest user
    ) {
        passwordCreateHandler.handle(request, user.getUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(
        name = "passwords.update",
        value = "/passwords",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> passwordsUpdate(
        @Valid @RequestBody PasswordUpdateRequest request,
        MutableHttpServletRequest user
    ) {
        passwordUpdateHandler.handle(request, user.getUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(
        name = "passwords.delete",
        value = "/passwords/{id}"
    )
    public ResponseEntity<Object> passwordsDelete(
        @PathVariable String id,
        MutableHttpServletRequest user
    ) {
        passwordDeleteHandler.handle(id, user.getUser());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
