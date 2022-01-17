package com.users.demo.infrastructure.api;

import com.users.demo.core.domain.User;
import com.users.demo.core.services.UserService;
import com.users.demo.infrastructure.builders.ConfirmationTokenDto;
import com.users.demo.infrastructure.builders.CredentialsDto;
import com.users.demo.infrastructure.builders.DtoBuilder;
import com.users.demo.infrastructure.builders.SuccessStatusDto;
import com.users.demo.infrastructure.builders.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CredentialsDto credentialsDto) {
        User user = userService.register(credentialsDto.getUsername(), credentialsDto.getPassword(), credentialsDto.getConfirmedPassword());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DtoBuilder.from(user));
    }

    @PostMapping("/confirm")
    public ResponseEntity<SuccessStatusDto> confirmAccount(@RequestBody ConfirmationTokenDto confirmationTokenDto) {
        userService.confirmUserAccount(confirmationTokenDto.getToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessStatusDto.builder().status("OK").message("Success").build());
    }
}
