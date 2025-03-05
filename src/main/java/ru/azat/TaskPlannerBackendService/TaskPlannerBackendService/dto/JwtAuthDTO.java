package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtAuthDTO {
    private String token;
    private String refreshToken;
}
