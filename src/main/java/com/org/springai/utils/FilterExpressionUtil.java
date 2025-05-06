package com.org.springai.utils;

import lombok.experimental.UtilityClass;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

@UtilityClass
public class FilterExpressionUtil {

    public static Filter.Expression createEqualFilterExpression(String key, String value) {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        return filter
                .eq(key, value)
                .build();
    }

    public static Filter.Expression createMultiFilterEqExpression() {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        // .and(filter.eq("brand", "Apple"), filter.lt("price", "1000"))
        return filter
                .and(filter.eq("brand", "Apple"), filter.eq("price", "1000"))
                .build();
    }

    public static Filter.Expression createMultiFilterInExpression() {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        return filter
                .in("brand", "Dell", "Apple")
                .build();
    }

    public static Filter.Expression createMultiFilterNotInExpression() {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        return filter
                .nin("brand", "Dell", "Apple")
                .build();
    }

    public static Filter.Expression createMultiFilterNotExpression() {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        return filter
                .not(filter.eq("brand", "Dell"))
                .build();
    }
}
