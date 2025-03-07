package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDTO markTaskAsDone(Long taskId);
    TaskDTO createTask(TaskDTO taskDTO);
    List<TaskDTO> getTasksByStatus(Long boardId, TaskStatus status);
    void deleteTask(Long taskId);
    List<TaskDTO> getAllTasksForBoard(Long boardId);
    TaskDTO updateTaskDescription(Long taskId, String newDescription);
}
