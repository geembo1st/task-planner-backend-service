package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.AuthenticationException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.RefreshTokenDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserCredentialsDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserProfile(Long userId);
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    UserDTO getUserById(Long id);
    User getCurrentUser();
    JwtAuthDTO singIn(UserCredentialsDTO userCredentialsDto);
    JwtAuthDTO refreshToken(RefreshTokenDTO refreshTokenDto);
}
