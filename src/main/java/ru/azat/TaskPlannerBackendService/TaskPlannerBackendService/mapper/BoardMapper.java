package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardRequestDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.BoardResponseDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Slf4j
public class BoardMapper {
    private final ModelMapper modelMapper;

    public Board toEntity(BoardRequestDTO boardRequestDTO, User createdBy) {
        log.debug("Конвертация DTO в Board: {}", boardRequestDTO.getTitle());
        Board board = modelMapper.map(boardRequestDTO, Board.class);
        board.setCreatedBy(createdBy);
        board.setCreatedAt(LocalDateTime.now());
        return board;
    }

    public BoardResponseDTO toDTO(Board board) {
        log.debug("Конвертация Board в DTO: {}", board.getId());
        return modelMapper.map(board, BoardResponseDTO.class);
    }
}

