package org.example.components.service;

import lombok.AllArgsConstructor;
import org.example.components.exception.controller.auth.IncorrectLoginException;
import org.example.components.exception.controller.auth.UsernameAlreadyTakenException;
import org.example.components.models.dto.auth.JwtDto;
import org.example.components.models.dto.auth.UserCredentialsDto;
import org.example.components.models.dto.auth.mapper.UserCredentialDtoMapper;
import org.example.components.models.entity.User;
import org.example.components.repository.UserRepository;
import org.example.components.security.JwtUtil;
import org.example.components.service.base.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultAuthService implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> registerUser(UserCredentialsDto userCredentialsDto) {
        User user = UserCredentialDtoMapper.mapFromDto(userCredentialsDto);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public ResponseEntity<JwtDto> loginUser(UserCredentialsDto userCredentialsDto) {
        User user = UserCredentialDtoMapper.mapFromDto(userCredentialsDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IncorrectLoginException("Incorrect login or password");
        }

        final String jwt = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtDto(jwt));
    }
}
