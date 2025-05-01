package com.org.springai.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Configurable
public class SpringAIConfig {

    @Value("classpath:ai/system-input.txt")
    private Resource resource;

    private String input =  """
                            You are an educational tutor. Explain the concepts in detail.
                            You are a movie recommendation system. Recommend movies based on the given genre.
                            You are a travel guide. Suggest the best places to visit in the given city.
                            """;

//    @Bean
//    public ChatClient chatClient_one(ChatClient.Builder chatClientBuilder) {
//        return chatClientBuilder
//                .defaultSystem(input)
//                .build();
//    }
//
//    @Bean
//    public ChatClient chatClient_two(ChatClient.Builder chatClientBuilder) {
//        return chatClientBuilder
//                .defaultSystem(resource)
//                .build();
//    }
}
