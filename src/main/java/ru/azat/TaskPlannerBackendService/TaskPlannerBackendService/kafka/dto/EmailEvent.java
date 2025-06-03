package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.kafka.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailEvent {
    private String to;
    private String subject;
    private String body;
}