package com.org.springai.controller;

import com.org.springai.service.AIAdvisorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AIAdvisorController {

    private final AIAdvisorsService aiAdvisorsService;

    @GetMapping("/start-chat")
    public void startChat() {
        log.info("Starting AI Advisors chat...");
        aiAdvisorsService.startChart();
    }
}
