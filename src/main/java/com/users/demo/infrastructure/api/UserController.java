package com.users.demo.infrastructure.api;

import com.users.demo.core.domain.User;
import com.users.demo.core.services.UserService;
import com.users.demo.core.services.security.AuthUserService;
import com.users.demo.infrastructure.builders.ConfirmationTokenDto;
import com.users.demo.infrastructure.builders.CredentialsDto;
import com.users.demo.infrastructure.builders.DtoBuilder;
import com.users.demo.infrastructure.builders.SuccessStatusDto;
import com.users.demo.infrastructure.builders.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CredentialsDto credentialsDto) {
        log.info("register a new user with email {}", credentialsDto.getUsername());
        User user = userService.register(credentialsDto.getUsername(), credentialsDto.getPassword(), credentialsDto.getConfirmedPassword());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DtoBuilder.from(user));
    }

    @PostMapping("/confirm")
    public ResponseEntity<SuccessStatusDto> confirmAccount(@RequestBody ConfirmationTokenDto confirmationTokenDto) {
        log.info("confirm user account");
        userService.confirmUserAccount(confirmationTokenDto.getToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessStatusDto.builder().status("OK").message("Success").build());
    }

    @PutMapping("/users/{user_id}")
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto, @PathVariable("user_id") Long userId) {
        log.info("update the user with identifier {}", userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DtoBuilder.from(userService.updateUserById(userDto, userId)));
    }
}
