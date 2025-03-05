package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;

    @Max(value = 200, message = "Название не больше 200 символов")
    @NotBlank(message = "Заполните поле")
    private String title;

    @Max(value = 300, message = "Описание не больше 200 символов")
    private String description;

    @Future(message = "Дата окончания не должна быть раньше настоящего времени")
    private LocalDateTime dueDate;

    @NotNull(message = "Доска обязательна")
    private Long boardId;
}
