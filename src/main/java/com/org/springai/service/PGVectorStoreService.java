package com.org.springai.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.org.springai.utils.SearchRequestUtil.createSearchRequest;
import static com.org.springai.utils.SpringAIUtils.loadDocument;

@Slf4j
@Service
public class PGVectorStoreService {

    @Value("classpath:/ai/vector-store/products.txt")
    private Resource productsResource;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public PGVectorStoreService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .build();
        this.vectorStore = vectorStore;
    }

    public String chatUsingVectorStore(String question) {
        log.info("Chat using Vector Store");
        return chatClient
                .prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    @SneakyThrows
    public void writeToVectorStore() {
        log.info("Creating RAG in postgres Vector Store");
        List<Document> documents = loadDocument(productsResource);
        vectorStore.add(documents);
    }

    public List<String> searchFromVectorStore() {
        log.info("Searching products from LLM using RAG");

        return vectorStore.similaritySearch(createSearchRequest("smartsearch with features like fitness tracking and health monitoring", 10))
                    .stream()
                    .map(document -> document.getContent())
                    .collect(Collectors.toList());
    }

}