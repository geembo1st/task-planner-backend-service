package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserCredentialsDTO {
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный email")
    String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    String password;
}
