package com.org.springai.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.springai.model.Product;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class SpringAIUtils {

    private TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();

    public static List<Document> splitDocument(Document document) {
        return tokenTextSplitter.split(document);
    }

    @SneakyThrows
    public static List<Document> loadDocument(Resource resource) {
        return Files
                .lines(resource.getFile().toPath())
                .map(Document::new)
                .collect(toList());
    }

    @SneakyThrows
    public static List<Document> loadDocumentWithMeta(Resource resource) {
        return Files
                .lines(resource.getFile().toPath())
                .map(line -> new Document(line, Map.of("meta1", "meta2")))
                .toList();
    }

    public static SearchRequest createSearchRequest(String query, int limit) {
        return SearchRequest
                .query(query)
                .withTopK(limit);
    }

    public static SearchRequest createFiltersSearchRequest(String query, Filter.Expression expression, int limit) {
        return SearchRequest
             .query(query)
             .withTopK(limit)
             .withFilterExpression(expression);
    }

    public static List<Product> getProducts(Resource resource) {
        List<Product> products = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                Product product = objectMapper.treeToValue(node, Product.class);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
