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

import static com.org.springai.utils.SearchRequestUtil.createSearchRequest;
import static com.org.springai.utils.SpringAIUtils.loadDocument;

@Slf4j
@Service
public class ChromaVectorStoreService {

    @Value("classpath:/ai/vector-store/books.txt")
    private Resource booksResource;

    private final VectorStore vectorStore;

    public ChromaVectorStoreService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @SneakyThrows
    public void writeToVectorStore() {
        log.info("Creating RAG in Chroma Vector Store");
        List<Document> documents = loadDocument(booksResource);
        vectorStore.add(documents);
    }

    public List<String> searchFromVectorStore() {
        log.info("Searching books from LLM using RAG");

        return vectorStore.similaritySearch(createSearchRequest("classic novel about wealth and society", 1))
                    .stream()
                    .map(document -> document.getContent())
                    .collect(Collectors.toList());
    }

}