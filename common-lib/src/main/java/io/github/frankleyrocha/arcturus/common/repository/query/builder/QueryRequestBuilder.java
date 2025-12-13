package io.github.frankleyrocha.arcturus.common.repository.query.builder;

import java.util.function.Consumer;

import io.github.frankleyrocha.arcturus.common.repository.query.QueryRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.AbstractFilter;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterAgregator;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterCondition;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterOperator;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PageRequest;

public class QueryRequestBuilder {
    private FilterRequestBuilder filterBuilder = new FilterRequestBuilder();
    private PageRequestBuilder pageRequestBuilder = new PageRequestBuilder();

    public <T> QueryRequestBuilder addCondition(String path, T filter) {
        return addCondition(builder -> builder.path(path).filter(filter));
    }

    public <T> QueryRequestBuilder addCondition(String path, FilterOperator op, T filter) {
        return addCondition(builder -> builder.path(path).operator(op).filter(filter));
    }

    public <T> QueryRequestBuilder addCondition(FilterCondition<T> condition) {
        this.filterBuilder.addCondition(condition);
        return this;
    }

    public <T> QueryRequestBuilder addCondition(Consumer<FilterConditionBuilder<T>> builderConsumer) {
        FilterConditionBuilder<T> builder = new FilterConditionBuilder<>();
        builderConsumer.accept(builder);
        FilterCondition<T> condition = builder.build();
        if (condition != null) {
            this.filterBuilder.addCondition(condition);
        }
        return this;
    }

    @SafeVarargs
    public final QueryRequestBuilder addConditions(Consumer<FilterConditionBuilder<?>>... builders) {
        for (Consumer<FilterConditionBuilder<?>> consumer : builders) {

            FilterConditionBuilder<?> builder = new FilterConditionBuilder<>();
            consumer.accept(builder);
            FilterCondition<?> condition = builder.build();
            if (condition != null) {
                this.filterBuilder.addCondition(condition);
            }

        }
        return this;
    }

    public <T> QueryRequestBuilder addConditionIf(boolean condition, Consumer<FilterConditionBuilder<T>> builderConsumer) {
        if (condition) addCondition(builderConsumer);
        return this;
    }

    public QueryRequestBuilder agregator(FilterAgregator agregator) {
        this.filterBuilder.agregator(agregator);
        return this;
    }

    public QueryRequestBuilder pageNumber(Integer pageNumber) {
        this.pageRequestBuilder.pageNumber(pageNumber);
        return this;
    }

    public QueryRequestBuilder pageSize(Integer pageSize) {
        this.pageRequestBuilder.pageSize(pageSize);
        return this;
    }

    public QueryRequestBuilder sortBy(String ...sortBy) {
        this.pageRequestBuilder.sortBy(sortBy);
        return this;
    }

    public <F extends AbstractFilter> QueryRequestBuilder filter(F filter) {
        return filter(filter.toDTO());
    }

    public QueryRequestBuilder filter(FilterRequest filter){

        agregator(filter.agregator());
        filter.conditions().forEach(filterCondition -> addCondition(filterCondition));

        return this;
    }

    public QueryRequestBuilder page(PageRequest page){
        pageNumber(page.pageNumber());
        pageSize(page.pageSize());
        sortBy(page.sortBy().toArray(String[]::new));
        return this;
    }

    public QueryRequest build() {
        return new QueryRequest(filterBuilder.build(), pageRequestBuilder.build());
    }
}