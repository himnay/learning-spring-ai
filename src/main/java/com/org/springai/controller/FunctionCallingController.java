package com.org.springai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/function")
public class FunctionCallingController {

    private final OpenAiChatModel openAiChatModel;
    
    @GetMapping("/chat")
    public String chatWithFunctionCalling() {
        UserMessage message = new UserMessage("What is the status of Booking for H001, H002 and H003?");
        // call the bean bookingStatus
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().withFunction("bookingStatus").build();
        ChatResponse bookingStatus = openAiChatModel.call(new Prompt(List.of(message), openAiChatOptions));
        log.info("Booking Status: {}", bookingStatus);
        return bookingStatus
                .getResult()
                .getOutput()
                .getContent();
    }

}