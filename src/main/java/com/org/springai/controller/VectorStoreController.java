package com.org.springai.controller;


import com.org.springai.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;

    @GetMapping("/vector")
    public String startVectorStore(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return vectorStoreService.chatUsingVectorStore(question);
    }
}
