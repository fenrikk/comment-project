package org.example.components.service.base;

import org.example.components.models.dto.auth.JwtDto;
import org.example.components.models.dto.auth.UserCredentialsDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> registerUser(UserCredentialsDto userCredentialsDto);

    ResponseEntity<JwtDto> loginUser(UserCredentialsDto userCredentialsDto);
}
