package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;

    @NotBlank(message = "Заполните поле")
    @Size(max = 50, message = "Имя пользователя не должно превышать 50 символов")
    private String name;

    @Size(max = 255, message = "Описание должено быть не более 255 символов")
    private String description;
}
