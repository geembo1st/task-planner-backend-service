package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.UserDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class UserMapper {
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    public User toEntity(UserDTO userDTO) {
        log.debug("Конвертация DTO в User: {}", userDTO.getId());
        User user = modelMapper.map(userDTO, User.class);

        if (userDTO.getBoardIds() != null && !userDTO.getBoardIds().isEmpty()) {
            List<Board> boards = boardRepository.findAllById(userDTO.getBoardIds());
            user.setBoards(boards);
        } else {
            user.setBoards(new ArrayList<>());
        }
         return user;
    }

    public UserDTO toDTO(User user) {
        log.debug("Конвертация User в DTO: {}", user.getId());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        if (user.getBoards() != null) {
            userDTO.setBoardIds(user.getBoards().stream()
                    .map(Board::getId)
                    .toList());
        } else {
            userDTO.setBoardIds(new ArrayList<>());
        }
        return userDTO;
    }
}
