package com.org.springai.controller;


import com.org.springai.service.ChromaVectorStoreService;
import com.org.springai.service.PGVectorStoreService;
import com.org.springai.service.RedisVectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vector")
@RequiredArgsConstructor
public class VectorStoreController {

    private final PGVectorStoreService vectorStoreService;
    private final ChromaVectorStoreService chromaVectorStoreService;
    private final RedisVectorStoreService redisVectorStoreService;

    @GetMapping("/list")
    public String startVectorStore(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return vectorStoreService.chatUsingVectorStore(question);
    }

    @PostMapping(value = "/pg/write")
    public void writeToPGVectorStore() {
        vectorStoreService.writeToVectorStore();
        log.info("Load to PG Vector Store successfully...");
    }

    @GetMapping(value = "/pg/read")
    public List<String> readFromPGVectorStore() {
        log.info("Search from PG Vector Store");
        return vectorStoreService.searchFromVectorStore();
    }

    @PostMapping(value = "/chrome/write")
    public void writeToChromeVectorStore() {
        chromaVectorStoreService.writeToVectorStore();
        log.info("Load to Chrome Vector Store successfully...");
    }

    @GetMapping(value = "/chrome/read")
    public List<String> readFromChromeVectorStore() {
        log.info("Search from Chrome Vector Store");
        return chromaVectorStoreService.searchFromVectorStore();
    }

    @PostMapping(value = "/redis/write")
    public void writeToRedisVectorStore() {
        redisVectorStoreService.writeToVectorStore();
        log.info("Load to Chrome Redis Store successfully...");
    }

    @GetMapping(value = "/redis/read")
    public List<String> readFromRedisVectorStore() {
        log.info("Search from Redis Vector Store");
        return redisVectorStoreService.searchFromVectorStore();
    }
}
