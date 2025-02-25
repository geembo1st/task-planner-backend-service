package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Board;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCreatedBy(User user);
}

