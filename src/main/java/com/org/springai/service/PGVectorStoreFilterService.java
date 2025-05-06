package com.org.springai.service;

import com.org.springai.model.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.org.springai.utils.SpringAIUtils.*;

@Slf4j
@Service
public class PGVectorStoreFilterService {

    @Value("classpath:/ai/vector-store/products.json")
    private Resource productsJSONResource;

    private final VectorStore vectorStore;

    public PGVectorStoreFilterService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @SneakyThrows
    public void writeToVectorStore() {
        log.info("Creating RAG in postgres Vector Store");
        List<Document> products = readAndPrintJsonFile();
        vectorStore.add(products);
    }

    public List<String> searchFromVectorStore() {
        log.info("Searching products from LLM using RAG");

        return vectorStore.similaritySearch(createSearchRequest("smartsearch with features like fitness tracking and health monitoring", 10))
                .stream()
                .map(document -> document.getContent())
                .collect(Collectors.toList());
    }

    public List<String> searchWithFilterExpression(String query) {
        // create a filter expression
        Filter.Expression expression = new FilterExpressionBuilder()
                .eq("brand", "Apple")
                .build();

        SearchRequest filtersSearchRequest = createFiltersSearchRequest(query, expression, 3);

        return vectorStore
                .similaritySearch(filtersSearchRequest)
                .stream()
                .map(Document::getContent)
                .toList();
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        List<Product> products = getProducts(productsJSONResource);

        for (Product product : products) {
            Document document = createDocument(product);
            documents.add(document);
        }
        return documents;
    }

    private Document createDocument(Product product) {
        return new Document(
                product.getBrand() + " " + product.getDescription(),
                Map.of("product_name"   , product.getProductName(),"brand"         , product.getBrand())
        );
    }

}