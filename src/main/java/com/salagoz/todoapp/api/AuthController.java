package com.salagoz.todoapp.api;

import com.salagoz.todoapp.model.LoginRequest;
import com.salagoz.todoapp.model.SignUpRequest;
import com.salagoz.todoapp.model.UserEntity;
import com.salagoz.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Manages Authentication operations.")
@CrossOrigin
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(description = "User login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String jwt = userService.login(loginRequest);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "User sign-up")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<UserEntity> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserEntity user = userService.registerUser(signUpRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
