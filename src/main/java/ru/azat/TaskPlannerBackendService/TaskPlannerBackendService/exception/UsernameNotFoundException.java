package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
