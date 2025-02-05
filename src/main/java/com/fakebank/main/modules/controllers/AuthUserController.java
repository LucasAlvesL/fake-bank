package com.fakebank.main.modules.controllers;

import com.fakebank.main.modules.dto.Auth.AuthUserRequestDTO;
import com.fakebank.main.modules.dto.User.UserRequestDTO;
import com.fakebank.main.modules.entities.UserEntity;
import com.fakebank.main.modules.services.AuthUserService;
import com.fakebank.main.modules.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auth")
@Schema(description = "Authentication Controller handling user login and registration.")
@Tag(name = "Authentication", description = "Authentication Controller")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Authenticate a user", description = "Validates user credentials and returns a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/login")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var token = this.authUserService.login(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @Operation(summary = "Register a new user", description = "Creates a new user in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid user input",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        var result = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
