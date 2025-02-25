package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Заполните поле")
    @Size(max = 50, message = "Имя пользователя не должно превышать 50 символов")
    private String username;

    @NotBlank(message = "Заполните поле")
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    private String password;

    @NotBlank(message = "Заполните поле")
    @Email(message = "Неверный формат")
    @Size(max = 255, message = "Имя пользователя не должно превышать 255 символов")
    private String email;
}
