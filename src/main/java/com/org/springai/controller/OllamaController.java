package com.org.springai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ollama")
@RequiredArgsConstructor
public class OllamaController {

    @Qualifier("ollamaChatClient")
    private final ChatClient chatClient;

    @GetMapping("/chat")
    public String getResponse(@RequestParam(value = "message", defaultValue = "What is the capital of Ireland?") String question) {
        log.info("Ollama Chat Client");
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