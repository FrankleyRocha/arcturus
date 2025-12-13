package io.github.frankleyrocha.arcturus.common.repository.query.builder;

import java.util.ArrayList;
import java.util.List;

import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterAgregator;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterCondition;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterRequest;

public class FilterRequestBuilder {
    private FilterAgregator agregator;
    private List<FilterCondition<?>> conditions = new ArrayList<>();

    public FilterRequestBuilder agregator(FilterAgregator agregator) {
        this.agregator = agregator;
        return this;
    }

    public FilterRequestBuilder addCondition(FilterCondition<?> condition) {
        this.conditions.add(condition);
        return this;
    }

    public FilterRequestBuilder addConditions(List<FilterCondition<?>> conditions) {
        this.conditions.addAll(conditions);
        return this;
    }

    public FilterRequest build() {
        return new FilterRequest(agregator, conditions);
    }
}