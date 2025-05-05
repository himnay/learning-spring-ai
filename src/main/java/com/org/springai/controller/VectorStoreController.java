package com.org.springai.controller;


import com.org.springai.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vector")
@RequiredArgsConstructor
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;

    @GetMapping("/list")
    public String startVectorStore(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return vectorStoreService.chatUsingVectorStore(question);
    }

    @PostMapping(value = "/write")
    public void loadVectorStore() {
        vectorStoreService.writeToPGVectorStore();
        log.info("Vector Store loaded successfully");
    }

    @GetMapping(value = "/read")
    public List<String> readVectorStore() {
        log.info("Search from PG Vector Store");
        return vectorStoreService.searchFromPGVectorStore();
    }
}
