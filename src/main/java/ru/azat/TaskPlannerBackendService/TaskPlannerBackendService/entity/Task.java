package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false, name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
