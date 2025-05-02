package com.org.springai.service;

import com.org.springai.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateChatPromptStringResponse(String question) {
        log.info("Generate Chat String Response");

        Prompt prompt = new Prompt(question);

        AssistantMessage message = chatClient
                .prompt(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();

        log.info("Message Type: {}", message.getMessageType());
        log.info("Message ToolCalls: {}", message.getToolCalls());
        log.info("Message Metadata: {}", message.getMetadata());
        log.info("Chat Response: {}", message.getContent());

        return message.getContent();
    }

    public List generateChatCallResponseOnAListFormat(String question) {
        log.info("Generate Chat Call List Response");

        List<String> message = chatClient
                .prompt()
                .user(question)
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));

        return message;
    }

    public Map<String, String> generateChatCallResponseOnAMapFormat(String question) {
        log.info("Generate Chat Call Map Response");

        Map<String, String> message = chatClient
                .prompt()
                .user(question)
                .call()
                .entity(new ParameterizedTypeReference<Map<String, String>>() {});

        return message;
    }

    public ChatResponse generateChatResponse(String question) {
        log.info("Generate Chat Response");

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(question)
                .call()
                .chatResponse();

        return chatResponse;
    }

    public ChatResponse generateTemplateChatResponse(String question) {

        String responseTemplate = "The response for you {question} is: ";

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(promptUserSpec -> promptUserSpec.text(responseTemplate)
                        .param("question", question))
                .call()
                .chatResponse();

        return chatResponse;
    }

    public List<Movie> generateTemplateMovieChatResponse(String director) {

        String responseTemplate = """
                                    "Generate a list of movies directed by {director}. If the director is unknown, return null.
                                    Each movie should include a title and year of release {format}"
                                  """;

        List<Movie> chatResponse = chatClient
                .prompt()
                .user(promptUserSpec -> promptUserSpec.text(responseTemplate)
                        .param("director", director)
                        .param("format", "json"))
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {
                });

        log.info("Chat Response: {}", chatResponse);

        return chatResponse;
    }

}
