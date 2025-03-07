package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.RefreshTokenDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserCredentialsDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.EmailAlreadyExistException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.UserNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.UsernameAlreadyExistException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.UsernameNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper.UserMapper;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.UserRepository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.security.jwt.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Получение списка всех пользователей");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserProfile(Long userId) {
        log.info("Получение профиля пользователя с id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        log.info("Добавление нового пользователя: {}", userDTO.getUsername());

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.warn("Попытка регистрации с уже существующим username: {}", userDTO.getUsername());
            throw new UsernameAlreadyExistException("Пользователь с таким username уже существует");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.warn("Попытка регистрации с уже существующим email: {}", userDTO.getEmail());
            throw new EmailAlreadyExistException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("Пользователь успешно зарегистрирован: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        log.info("Обновление пользователя с id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        userRepository.save(user);
        log.info("Пользователь обновлен: {}", userId);
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Попытка удаления пользователя с id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        userRepository.delete(user);
        log.info("Пользователь с id:{} успешно удален", userId);
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Получение пользователя по id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return userMapper.toDTO(user);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Получение текущего пользователя по email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    @Override
    public JwtAuthDTO singIn(UserCredentialsDTO userCredentialsDto) throws AuthenticationException {
        log.info("Попытка входа пользователя: {}", userCredentialsDto.getUsername());
        return null;
    }

    @Override
    public JwtAuthDTO refreshToken(RefreshTokenDTO refreshTokenDto) {
        log.info("Попытка обновления токена");
        return null;
    }
}
