package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.RefreshTokenDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserCredentialsDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserRegistrationDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.kafka.dto.EmailEvent;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.kafka.producer.KafkaProducerService;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.AuthenticationService;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.UserService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthDTO> login(@Valid @RequestBody UserCredentialsDTO credentials) {
        log.info("Попытка входа пользователя -> {}", credentials.getEmail());
        JwtAuthDTO jwtAuthDTO = authenticationService.authenticate(credentials);
        EmailEvent event = new EmailEvent(credentials.getEmail(), "Добро пожаловать!", "Вы успешно вошли в систему, " + credentials.getEmail());
        kafkaProducerService.sendEmailEvent(event);
        log.info("Событие отправлено в Kafka для пользователя {}", credentials.getEmail());
        return ResponseEntity.ok(jwtAuthDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthDTO> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        log.info("Регистрация пользователя -> {}", userRegistrationDTO.getEmail());
        JwtAuthDTO jwtAuthDTO = authenticationService.register(userRegistrationDTO);

        EmailEvent event = new EmailEvent(userRegistrationDTO.getEmail(), "Добро пожаловать!", "Спасибо за регистрацию, " + userRegistrationDTO.getEmail());
        kafkaProducerService.sendEmailEvent(event);
        log.info("Событие отправлено в Kafka для пользователя {}", userRegistrationDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtAuthDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthDTO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        log.info("Запрос на обновление токена");
        JwtAuthDTO jwtAuthDTO = userService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(jwtAuthDTO);
    }
}
