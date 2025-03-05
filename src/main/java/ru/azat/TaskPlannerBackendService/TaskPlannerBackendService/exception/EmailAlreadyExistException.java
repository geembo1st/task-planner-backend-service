package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
