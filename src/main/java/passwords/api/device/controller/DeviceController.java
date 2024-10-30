package passwords.api.device.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import passwords.api.configuration.MutableHttpServletRequest;
import passwords.api.device.handler.DeviceListHandler;
import passwords.api.user.dto.response.AuthenticationResponse;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class DeviceController {
    private final DeviceListHandler deviceListHandler;

    @GetMapping(
        name = "devices.list",
        value = "/devices",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArrayList<Object>> devicesList(
        MutableHttpServletRequest request
    ) {
        return new ResponseEntity<>(deviceListHandler.handle(request.getUser()), HttpStatus.OK);
    }
}
