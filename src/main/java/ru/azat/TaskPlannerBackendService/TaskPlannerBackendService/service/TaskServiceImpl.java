package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Task;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.BoardNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.TaskNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper.TaskMapper;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.BoardRepository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public TaskDTO markTaskAsDone(Long taskId) {
        log.info("Изменение статуса задачи на 'DONE', ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
        task.setStatus(TaskStatus.DONE);
        TaskDTO result = taskMapper.toDTO(taskRepository.save(task));
        log.info("Задача ID {} помечена как 'DONE'", taskId);
        return result;
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Создание новой задачи: {}", taskDTO);
        Board board = boardRepository.findById(taskDTO.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("Доска не найдена"));

        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(TaskStatus.NEW)
                .dueDate(taskDTO.getDueDate())
                .board(board)
                .build();

        TaskDTO result = taskMapper.toDTO(taskRepository.save(task));
        log.info("Задача создана: {}", result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByStatus(Long boardId, TaskStatus status) {
        log.info("Получение задач для доски ID {}, со статусом статус: {}", boardId, status);
        return taskRepository.findByBoardIdAndStatus(boardId, status).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        log.info("Удаление задачи ID: {}", taskId);
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Задача не найдена");
        }
        taskRepository.deleteById(taskId);
        log.info("Задача ID {} удалена", taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasksForBoard(Long boardId) {
        log.info("Получение всех задач для доски ID: {}", boardId);
        return taskRepository.findByBoardId(boardId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO updateTaskDescription(Long taskId, String newDescription) {
        log.info("Обновление описания задачи ID {}: {}", taskId, newDescription);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));

        task.setDescription(newDescription);
        TaskDTO result = taskMapper.toDTO(taskRepository.save(task));
        log.info("Описание задачи ID {} обновлено", taskId);
        return result;
    }

    @Override
    public TaskDTO getTasksById(Long taskId) {
        log.info("Получение задачи по ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
        TaskDTO taskDTO = taskMapper.toDTO(task);
        return taskDTO;
    }
}

