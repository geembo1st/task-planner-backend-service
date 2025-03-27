package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.RefreshTokenDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    User getCurrentUser();

    JwtAuthDTO refreshToken(RefreshTokenDTO refreshTokenDto);
}
