package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
