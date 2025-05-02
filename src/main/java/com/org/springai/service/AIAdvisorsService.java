package com.org.springai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Slf4j
@Service
public class AIAdvisorsService {

    private final ChatClient chatClient;

    /**
     * Create a chatClient with MessageChatMemoryAdvisor
     * Chat conversion with LLM are stateless by default.
     * To make them stateful, you can use a memory advisor.
     * @param chatClientBuilder
     */
    public AIAdvisorsService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public void startChart() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to AI Advisors! Type 'exit' to quit.");
        System.out.println("Please enter your question:");
        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Exiting AI Advisors. Goodbye!");
                break;
            }
            String response = getResponse(message);
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }

    private String getResponse(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}