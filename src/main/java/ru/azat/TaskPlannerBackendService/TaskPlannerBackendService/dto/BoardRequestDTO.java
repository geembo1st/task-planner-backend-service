package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO {
    @NotBlank(message = "Заполните поле")
    @Size(max = 100, message = "Название доски не должно превышать 100 символов")
    private String title;

    @Size(max = 255, message = "Описание должно быть не более 255 символов")
    private String description;
}

