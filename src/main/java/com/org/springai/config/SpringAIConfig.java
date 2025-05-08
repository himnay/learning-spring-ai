package com.org.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class SpringAIConfig {

    @Value("classpath:ai/vector-store/basic.txt")
    private Resource inputResource;

    @Bean
    @Qualifier("ollamaChatClient")
    public ChatClient ollamaChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .build();
    }

}
