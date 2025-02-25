package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Task;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByBoardId(Long boardId);
}

