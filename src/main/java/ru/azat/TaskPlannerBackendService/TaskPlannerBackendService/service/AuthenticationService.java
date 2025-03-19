package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserCredentialsDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserRegistrationDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Role;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.EmailAlreadyExistException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.UsernameAlreadyExistException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.UsernameNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.RoleRepository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.UserRepository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.security.jwt.JwtService;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final String DEFAULT_ROLE = "ROLE_USER";

    @Transactional
    public JwtAuthDTO register(UserRegistrationDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.error("Регистрация у пользователя:{} не прошла", userDTO.getUsername());
            throw new EmailAlreadyExistException("Email уже используется");
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.error("Регистрация у пользователя:{} не прошла", userDTO.getUsername());
            throw new UsernameAlreadyExistException("Имя пользователя уже используется");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.setRoles(Set.of(defaultRole));

        log.info("Регистрация у пользователя {} прошла успешно", user.getUsername());
        userRepository.save(user);
        return jwtService.generateAuthToken(user);
    }

    @Transactional(readOnly = true)
    public JwtAuthDTO authenticate(UserCredentialsDTO credentials) {
        User user = userRepository.findByEmail(credentials.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPasswordHash())) {
            log.error("Неверный пароль у пользователя: {}", credentials.getEmail());
            throw new BadCredentialsException("Неверный пароль");
        }

        log.info("Аутентификация у пользователя: {} прошла успешно", credentials.getEmail());
        return jwtService.generateAuthToken(user);
    }

}

