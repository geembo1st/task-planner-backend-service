package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardRequestDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardResponseDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service.BoardService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(@Valid @RequestBody BoardRequestDTO boardRequestDTO) {
        log.info("Запрос на создание новой доски");
        BoardResponseDTO createdBoard = boardService.createBoard(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long boardId) {
        log.info("Запрос на получение доски с ID: {}", boardId);
        BoardResponseDTO boardDTO = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        log.info("Запрос на получение всех досок");
        List<BoardResponseDTO> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardResponseDTO>> getBoardsByUser(@PathVariable Long userId) {
        log.info("Запрос на получение досок для пользователя с ID: {}", userId);
        List<BoardResponseDTO> boards = boardService.getBoardsByUser(userId);
        return ResponseEntity.ok(boards);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardRequestDTO boardRequestDTO) {
        log.info("Запрос на обновление доски с ID: {}", boardId);
        BoardResponseDTO updatedBoard = boardService.updateBoard(boardId, boardRequestDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        log.info("Запрос на удаление доски с ID: {}", boardId);
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksForBoard(@PathVariable Long boardId) {
        log.info("Запрос на получение всех задач для доски с ID: {}", boardId);
        List<TaskDTO> tasks = boardService.getAllTasksForBoard(boardId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{boardId}/tasks/status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Long boardId, @RequestParam TaskStatus status) {
        log.info("Запрос на получение задач для доски с ID: {}, по статусу: {}", boardId, status.name());
        List<TaskDTO> tasks = boardService.getTasksByStatus(boardId, status);
        return ResponseEntity.ok(tasks);
    }
}
