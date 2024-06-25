package org.example.components.models.dto.auth.mapper;

import org.example.components.models.dto.auth.UserCredentialsDto;
import org.example.components.models.entity.User;

public class UserCredentialDtoMapper {
    public static User mapFromDto(UserCredentialsDto userCredentialsDto) {
        User user = new User();
        user.setUsername(userCredentialsDto.getUsername());
        user.setPassword(userCredentialsDto.getPassword());
        return user;
    }
}
