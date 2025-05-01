package com.org.springai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateChatPromptResponse(String question) {
        log.info("Generate Chat Prompt Response");

        Prompt prompt = new Prompt(question);

        AssistantMessage message = chatClient
                .prompt(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();

        log.info("Message Type: {}", message.getMessageType());
        log.info("Message ToolCalls: {}", message.getToolCalls());
        log.info("Message Metadata: {}", message.getMetadata());
        log.info("Chat Response: {}", message.getContent());

        return message.getContent();
    }

    public String generateChatCallResponse(String question) {
        log.info("Generate Chat Call Response");

        AssistantMessage message = chatClient
                .prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();

        return message.getContent();
    }

    public ChatResponse generateChatResponse(String question) {
        log.info("Generate Chat Response");

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(question)
                .call()
                .chatResponse();

        return chatResponse;
    }

    public ChatResponse generateChatTemplate(String question) {

        String responseTemplate = "The response for you {question} is: ";

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(promptUserSpec -> promptUserSpec.text(responseTemplate)
                        .param("question", question))
                .call()
                .chatResponse();

        return chatResponse;
    }



}
