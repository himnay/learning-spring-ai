package com.org.springai.utils;

import lombok.experimental.UtilityClass;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;

@UtilityClass
public class SearchRequestUtil {

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

    public static SearchRequest textQuerySearchRequest(String query, String expression) {
        return SearchRequest.query(query)
                .withFilterExpression(expression)
                .withTopK(3);
    }
}
