package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRegistrationDTO {
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(max = 50, message = "Имя пользователя должно содержать до 50 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Имя пользователя может содержать только буквы, цифры и _")
    String username;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный email")
    String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    String password;
}
