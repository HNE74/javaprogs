package de.hne.springaieval.controller;

import de.hne.springaieval.service.RagService;
import org.springframework.ai.client.Generation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ai/rag")
    public Generation generate(@RequestParam(value = "message", defaultValue = "What is the best citybike")
                                   String message) {
        return ragService.retrieve(message);
    }
}
