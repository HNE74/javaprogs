package de.hne.springaieval.service;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.ai.retriever.VectorStoreRetriever;
import org.springframework.ai.vectorstore.InMemoryVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Retrieval Augmented Generation service.
 */
@Service
public class RagService {
    private final AiClient aiClient;
    private final EmbeddingClient embeddingClient;

    private VectorStore vectorStore;

    private final Resource bikesResource;

    private final Resource systemBikePrompt;

    public RagService(AiClient aiClient, EmbeddingClient embeddingClient, ApplicationContext ctx) {
        this.aiClient = aiClient;
        this.embeddingClient = embeddingClient;

        this.bikesResource = ctx.getResource("classpath:/bikes.json");
        this.systemBikePrompt = ctx.getResource("classpath:/system-qa.st");

        try {
            setupVectorStore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupVectorStore() throws IOException {
        var docs = List.of(new Document(bikesResource.getContentAsString(Charset.defaultCharset())));
        vectorStore = new InMemoryVectorStore(embeddingClient);
        vectorStore.add(docs);
    }

    private Message fetchSystemMessage(List<Document> similarDocuments){
        var documents = similarDocuments.stream().map(
                doc -> doc.getContent()).collect(Collectors.joining("\n"));
        var template = new SystemPromptTemplate(systemBikePrompt);
        return template.createMessage(Map.of("documents", documents));
    }

    public Generation retrieve(String message) {
        var retriever = new VectorStoreRetriever(vectorStore);
        List<Document> similarDocuments = retriever.retrieve(message);

        var systemMessage = fetchSystemMessage(similarDocuments);
        var userMessage = new UserMessage(message);

        var msgList = List.of(systemMessage, userMessage);
        var prompt = new Prompt(msgList);
        var response = aiClient.generate(prompt);
        return response.getGeneration();
    }
}
