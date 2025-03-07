package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
