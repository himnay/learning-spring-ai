package com.org.springai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VectorStoreService {

    private final ChatClient chatClient;

    public VectorStoreService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .build();
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






}