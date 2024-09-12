package com.example.authorizationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private KafkaTemplate<String, Object> kafkaTemplate;
    private ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserSignInMail(Object userSignInEmailDto) {
        System.out.println("producer called");
        try {
            kafkaTemplate.send("user-sign-in", objectMapper.writeValueAsString(userSignInEmailDto));
        } catch (JsonProcessingException e) {
            System.out.println("Error publishing msg to kafka");
            throw new RuntimeException(e);
        }
    }
}
