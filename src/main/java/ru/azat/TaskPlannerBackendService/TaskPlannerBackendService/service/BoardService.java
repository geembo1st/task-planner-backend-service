package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardRequestDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardResponseDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;

import java.util.List;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO);
    BoardResponseDTO getBoardById(Long boardId);
    List<BoardResponseDTO> getAllBoards();
    List<BoardResponseDTO> getBoardsByUser(Long userId);
    BoardResponseDTO updateBoard(Long boardId, BoardRequestDTO boardRequestDTO);
    void deleteBoard(Long boardId);
    List<TaskDTO> getAllTasksForBoard(Long boardId);
    List<TaskDTO> getTasksByStatus(Long boardId, TaskStatus status);
}
