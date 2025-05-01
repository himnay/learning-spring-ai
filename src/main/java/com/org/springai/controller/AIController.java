package com.org.springai.controller;

import com.org.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AIController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String getResponse(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateChatPromptResponse(question);
    }

    @GetMapping("/chat-response")
    public ChatResponse getChatResponse(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String message) {
        return chatService.generateChatResponse(message);
    }

    @GetMapping("/chat-template")
    public ChatResponse getChatTemplateResponse(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String message) {
        return chatService.generateChatTemplate(message);
    }

}
