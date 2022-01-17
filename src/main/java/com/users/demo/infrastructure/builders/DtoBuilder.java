package com.users.demo.infrastructure.builders;

import com.users.demo.core.domain.User;

public class DtoBuilder {

    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .photoUrl(user.getPhotoUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
