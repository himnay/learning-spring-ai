package com.org.springai.controller;


import com.org.springai.service.ChromaVectorStoreService;
import com.org.springai.service.PGVectorStoreFilterService;
import com.org.springai.service.PGVectorStoreService;
import com.org.springai.service.RedisVectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.org.springai.utils.FilterExpressionUtil.*;

@Slf4j
@RestController
@RequestMapping("/vector")
@RequiredArgsConstructor
public class VectorStoreController {

    private final PGVectorStoreService pgVectorStoreService;
    private final PGVectorStoreFilterService pgVectorStoreFilterService;
    private final ChromaVectorStoreService chromaVectorStoreService;
    private final RedisVectorStoreService redisVectorStoreService;

    @GetMapping("/list")
    public String startVectorStore(@RequestParam(value = "message", defaultValue = "give me the names of top five cities in US ?") String question) {
        return pgVectorStoreService.chatUsingVectorStore(question);
    }

    @PostMapping(value = "/pg/write")
    public void writeToPGVectorStore() {
        pgVectorStoreService.writeToVectorStore();
        log.info("Load to PG Vector Store successfully...");
    }

    @PostMapping(value = "/pg/write/json")
    public void writeJSONToPGVectorStore() {
        pgVectorStoreFilterService.writeToVectorStore();
        log.info("Load JSON to PG Vector Store successfully...");
    }

    @GetMapping(value = "/pg/read")
    public List<String> readFromPGVectorStore() {
        log.info("Read from PG Vector Store");
        return pgVectorStoreService.searchFromVectorStore();
    }

    @GetMapping(value = "/pg/search")
    public List<String> searchFromPGVectorStore(@RequestParam(defaultValue = "battery") String query) {
        log.info("Search from PG Vector Store");
        Filter.Expression filterExpression = createEqualFilterExpression("brand", "Apple");
        // OR
        Filter.Expression multiFilterExpression = createMultiFilterEqExpression();
        // OR
        Filter.Expression multiFilterInExpression = createMultiFilterInExpression();
        // OR
        Filter.Expression multiFilterNotInExpression = createMultiFilterNotInExpression();
        // OR
        Filter.Expression multiFilterNotExpression = createMultiFilterNotExpression();
        // OR
        String expression = "brand in ['Apple', 'Samsung'] and price > 1000";
        // return pgVectorStoreFilterService.searchWithEqualFilterExpression(query, multiFilterNotExpression);
        // OR
        return pgVectorStoreFilterService.searchWithEqualTextFilterExpression(query, expression);
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
