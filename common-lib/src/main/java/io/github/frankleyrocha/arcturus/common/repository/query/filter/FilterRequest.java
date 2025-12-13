package io.github.frankleyrocha.arcturus.common.repository.query.filter;

import java.util.ArrayList;
import java.util.List;

public record FilterRequest(
    FilterAgregator agregator,
    List<FilterCondition<?>> conditions
) {

    public FilterRequest {
        if( agregator == null) agregator = FilterAgregator.AND;
        if( conditions == null) conditions = new ArrayList<>();
    }

    public FilterRequest(FilterAgregator agregator){
        this(agregator, null);
    }

    public FilterRequest(List<FilterCondition<?>> conditions){
        this(null, conditions);
    }

    public FilterRequest(){
        this(null, null);
    }

}
