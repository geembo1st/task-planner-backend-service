package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserCredentialsDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.AuthenticationService;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.UserServiceImpl;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthDTO> login(@RequestBody UserCredentialsDTO credentials) {
        log.info("Аунтификация пользователя -> {}", credentials.getUsername());
        JwtAuthDTO jwtAuthDTO = authenticationService.authenticate(credentials);
        return ResponseEntity.ok(jwtAuthDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthDTO> register(@Valid @RequestBody UserDTO userDTO) {
        log.info("Регистрация пользователя -> {}", userDTO.getUsername());
        JwtAuthDTO jwtAuthDTO = authenticationService.register(userDTO);
        return ResponseEntity.ok(jwtAuthDTO);
    }
}
