package com.org.springai.controller;

import com.org.springai.model.Movie;
import com.org.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String generateChatPromptStringResponse(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateChatPromptStringResponse(question);
    }

    @GetMapping("/chat-list")
    public List generateChatCallResponseOnAListFormat(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateChatCallResponseOnAListFormat(question);
    }

    @GetMapping("/chat-map")
    public Map<String, String> generateChatCallResponseOnAMapFormat(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateChatCallResponseOnAMapFormat(question);
    }

    @GetMapping("/chat-response")
    public ChatResponse getChatResponse(@RequestParam(value = "question", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateChatResponse(question);
    }

    @GetMapping("/chat-city-template")
    public ChatResponse getChatTemplateResponse(@RequestParam(value = "question", defaultValue = "give me the names of top five cities in US ?") String question) {
        return chatService.generateTemplateChatResponse(question);
    }

    @GetMapping("/chat-movie-template")
    public List<Movie> getChatTemplateMovieResponse(@RequestParam(value = "director") String director) {
        return chatService.generateTemplateMovieChatResponse(director);
    }
}
