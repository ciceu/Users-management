package com.users.demo.infrastructure.builders;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
@Getter
public class UserDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photoUrl;
    private LocalDateTime birthdate;
}
