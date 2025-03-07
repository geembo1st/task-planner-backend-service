package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
