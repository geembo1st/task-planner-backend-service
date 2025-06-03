package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.kafka.dto.EmailEvent;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmailEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailEvent(EmailEvent emailEvent) {
        kafkaTemplate.send("email-topic", emailEvent);
    }
}
