package io.github.frankleyrocha.arcturus.common.repository.query.builder;

import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterCondition;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterOperator;

public class FilterConditionBuilder<T> {
    private String path;
    private Boolean negate;
    private FilterOperator op;
    private Boolean ignoreCase;
    private T filter;

    public FilterConditionBuilder<T> path(String path) {
        this.path = path;
        return this;
    }

    public FilterConditionBuilder<T> negate(Boolean negate) {
        this.negate = negate;
        return this;
    }

    public FilterConditionBuilder<T> operator(FilterOperator op) {
        this.op = op;
        return this;
    }

    public FilterConditionBuilder<T> ignoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }

    public FilterConditionBuilder<T> filter(T filter) {
        this.filter = filter;
        return this;
    }

    public FilterCondition<T> build() {
        return new FilterCondition<>(path, negate, op, ignoreCase, filter);
    }
}
