package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
