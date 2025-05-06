package com.org.springai.config;

import com.org.springai.model.BookingRequest;
import com.org.springai.model.BookingResponse;
import com.org.springai.service.FunctionCallingService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Configuration
public class SpringAIConfig {

    @Value("classpath:ai/vector-store/basic.txt")
    private Resource inputResource;

    private String input =  """
                            You are an educational tutor. Explain the concepts in detail.
                            You are a movie recommendation system. Recommend movies based on the given genre.
                            You are a travel guide. Suggest the best places to visit in the given city.
                            """;

//    @Bean
//    public RedisVectorStore vectorStore(EmbeddingModel embeddingModel, JedisConnectionFactory jedisConnectionFactory) {
//        RedisVectorStore.RedisVectorStoreConfig withMetadataFields = getRedisVectorStoreConfig("meta2");
//        JedisPooled jedisPooled = new JedisPooled(jedisConnectionFactory.getHostName(), jedisConnectionFactory.getPort());
//        return new RedisVectorStore(withMetadataFields, embeddingModel, jedisPooled, true);
//    }

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) throws IOException {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
        File file = new File("/Users/himansu/learning-spring-ai/src/main/resources/ai/vector-store.json");
        if(file.exists()) {
            simpleVectorStore.load(file);
        } else {
            TextReader textReader = getTextReader(inputResource);
            List<Document> documents = getDocuments(textReader);
            simpleVectorStore.add(documents);
            simpleVectorStore.save(file);
        }
        return simpleVectorStore;
    }

    /**
     *
     * @param resource
     * @return
     */
    private TextReader getTextReader(Resource resource) {
        TextReader textReader = new TextReader(inputResource);
        textReader.getCustomMetadata().put("filename", inputResource.getFilename());
        return textReader;
    }

    /**
     * Split the text into smaller chunks using text splitter
     * @param textReader
     * @return
     */
    private List<Document> getDocuments(TextReader textReader) {
        TextSplitter textSplitter = new TokenTextSplitter();
        return textSplitter.apply(textReader.get());
    }

    @Bean
    @Qualifier("chantSystemResource")
    public ChatClient chatClient_one(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(inputResource)
                .build();
    }

    @Bean
    @Description("Get the status of a hotel booking")
    public Function<BookingRequest, BookingResponse> bookingStatus(){
        return new FunctionCallingService();
    }

//    private RedisVectorStore.RedisVectorStoreConfig getRedisVectorStoreConfig(String tag) {
//        return RedisVectorStore
//                .RedisVectorStoreConfig
//                .builder()
//                .withMetadataFields(RedisVectorStore.MetadataField.tag(tag))
//                .build();
//    }

}
