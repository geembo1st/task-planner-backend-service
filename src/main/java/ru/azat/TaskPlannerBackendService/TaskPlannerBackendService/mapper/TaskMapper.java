package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Task;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;

@Component
@AllArgsConstructor
@Slf4j
public class TaskMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.createTypeMap(String.class, TaskStatus.class)
                .setConverter(ctx -> TaskStatus.valueOf(ctx.getSource()));

        modelMapper.createTypeMap(TaskStatus.class, String.class)
                .setConverter(ctx -> ctx.getSource().name());
    }

    public Task toEntity(TaskDTO taskDTO, Board board) {
        log.debug("Конвертация DTo в Task: {}", taskDTO.getId());
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setBoard(board);
        return task;
    }

    public TaskDTO toDTO(Task task) {
        log.debug("Конвертация Task в DTO: {}", task.getId());
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        taskDTO.setBoardId(taskDTO.getBoardId());
        return taskDTO;
    }
}
