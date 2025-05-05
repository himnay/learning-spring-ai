package com.org.springai.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.org.springai.utils.SpringAIUtils.*;

@Slf4j
@Service
public class RedisVectorStoreService {

    @Value("classpath:/ai/vector-store/bikes.txt")
    private Resource bikesResource;

    private final VectorStore vectorStore;

    public RedisVectorStoreService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @SneakyThrows
    public void writeToVectorStore() {
        log.info("Creating RAG in Redis Vector Store");
        List<Document> documents = loadDocumentWithMeta(bikesResource);
        vectorStore.add(documents);
    }

    public List<String> searchFromVectorStore() {
        log.info("Searching bikes from LLM using RAG");

        return vectorStore.similaritySearch(createSearchRequest("Bike for small kids", 1))
                    .stream()
                    .map(document -> document.getContent())
                    .collect(Collectors.toList());
    }

}