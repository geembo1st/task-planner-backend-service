package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{boardId}/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Long boardId, @PathVariable TaskStatus status) {
        log.info("Запрос на получение задач для доски ID {}, статус: {}", boardId, status);
        List<TaskDTO> tasks = taskService.getTasksByStatus(boardId, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.getTasksById(taskId);
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long boardId, @Valid @RequestBody TaskDTO taskDTO) {
        log.info("Запрос на создание задачи для доски ID: {}", boardId);
        taskDTO.setBoardId(boardId);
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PatchMapping("/{taskId}/done")
    public ResponseEntity<TaskDTO> markTaskAsDone(@PathVariable Long taskId) {
        log.info("Запрос на пометку задачи ID {} как выполненной", taskId);
        TaskDTO updatedTask = taskService.markTaskAsDone(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        log.info("Запрос на удаление задачи ID {}", taskId);
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/description")
    public ResponseEntity<TaskDTO> updateTaskDescription(@PathVariable Long taskId, @RequestParam String description) {
        log.info("Запрос на обновление описания задачи ID {}: {}", taskId, description);
        TaskDTO updatedTask = taskService.updateTaskDescription(taskId, description);
        return ResponseEntity.ok(updatedTask);
    }
}

