package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskPlannerBackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskPlannerBackendServiceApplication.class, args);
	}

}
