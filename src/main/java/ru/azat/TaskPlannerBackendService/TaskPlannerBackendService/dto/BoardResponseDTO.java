package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}