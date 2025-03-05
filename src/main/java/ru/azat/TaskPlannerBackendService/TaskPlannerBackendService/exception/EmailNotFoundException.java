package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
