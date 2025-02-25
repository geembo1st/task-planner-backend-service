package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
