package de.hne.springaieval.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.Generation;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class SimpleAiController {

    private final AiClient aiClient;
    @Value("classpath:/bikes.json")
    private Resource bikesResource;

    @Value("classpath:/system-qa.st")
    private Resource systemBikePrompt;
    @Value("classpath:/my-prompt.st")
    private Resource promptResource;

    public SimpleAiController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/ai/simple")
    public Completion completion(@RequestParam(value = "message",
    defaultValue = "Was ist Java?") String message) {
        return new Completion(aiClient.generate(message));
    }

    @GetMapping("/ai/prompt")
    public Generation completion2(@RequestParam(value = "topic",
            defaultValue = "data science") String topic) {
        var promptTemplate = new PromptTemplate(promptResource);
        var prompt = promptTemplate.create(Map.of("topic", topic));
        return aiClient.generate(prompt).getGeneration();
    }

    @GetMapping("/ai/output")
    public ActorsFilms generate(@RequestParam(value = "actor", defaultValue = "Chuck Norris") String actor) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class);

        var format = outputParser.getFormat();
        log.info("Output parser format is: {}", format);
        var template = """
                Erzeuge die Filmografie für den Schauspieler {actor}.
                {format}
                """;

        var promptTemplate = new PromptTemplate(template, Map.of("actor", actor, "format", format));
        var prompt = new Prompt(promptTemplate.createMessage());
        var generation = aiClient.generate(prompt).getGeneration();

        var actorsFilms = outputParser.parse(generation.getText());
        return actorsFilms;
    }

    private record Completion(String message) {}

    private record ActorsFilms(String actor, List<String> movies) {};
}


