package org.example.components.controller;

import lombok.AllArgsConstructor;
import org.example.components.models.dto.auth.JwtDto;
import org.example.components.models.dto.auth.UserCredentialsDto;
import org.example.components.service.base.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/auth"))
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserCredentialsDto user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> loginUser(@RequestBody UserCredentialsDto user) {
        return authService.loginUser(user);
    }
}
