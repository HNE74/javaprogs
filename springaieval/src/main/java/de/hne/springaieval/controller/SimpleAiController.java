package de.hne.springaieval.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SimpleAiController {

    private final AiClient aiClient;

    public SimpleAiController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/ai/simple")
    public Completion completion(@RequestParam(value = "message",
    defaultValue = "Was ist Java?") String message) {
        return new Completion(aiClient.generate(message));
    }

    public record Completion(String message) {}
}


