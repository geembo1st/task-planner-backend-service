package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardRequestDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardResponseDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.TaskDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.enums.TaskStatus;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception.BoardNotFoundException;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper.BoardMapper;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper.TaskMapper;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper.UserMapper;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.BoardRepository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;
    private final BoardMapper boardMapper;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO) {
        log.info("Создание новой доски: {}", boardRequestDTO.getTitle());

        User currentUser = userService.getCurrentUser();
        Board board = boardMapper.toEntity(boardRequestDTO, currentUser);
        board = boardRepository.save(board);

        log.info("Доска создана с ID: {}", board.getId());
        return boardMapper.toDTO(board);
    }

    @Override
    public BoardResponseDTO getBoardById(Long boardId) {
        log.info("Получение доски с ID: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Доска не найдена"));

        log.info("Найдена доска: {}", board.getTitle());
        return boardMapper.toDTO(board);
    }

    @Override
    public List<BoardResponseDTO> getAllBoards() {
        log.info("Получение всех досок");
        return boardRepository.findAll().stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDTO> getBoardsByUser(Long userId) {
        log.info("Получение досок пользователя с ID: {}", userId);
        User user = userMapper.toEntity(userService.getUserById(userId));
        return boardRepository.findByCreatedBy(user).stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponseDTO updateBoard(Long boardId, BoardRequestDTO boardRequestDTO) {
        log.info("Обновление доски с ID: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Доска не найдена"));

        board.setTitle(boardRequestDTO.getTitle());
        board.setDescription(boardRequestDTO.getDescription());
        board = boardRepository.save(board);

        log.info("Доска обновлена: {}", board.getId());
        return boardMapper.toDTO(board);
    }

    @Override
    public void deleteBoard(Long boardId) {
        log.info("Удаление доски с ID: {}", boardId);
        boardRepository.deleteById(boardId);
    }

    @Override
    public List<TaskDTO> getAllTasksForBoard(Long boardId) {
        log.info("Получение задач для доски с ID: {}", boardId);
        return taskRepository.findByBoardId(boardId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByStatus(Long boardId, TaskStatus status) {
        log.info("Получение задач для доски с ID: {} со статусом: {}", boardId, status);
        return taskRepository.findByBoardIdAndStatus(boardId, status).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
