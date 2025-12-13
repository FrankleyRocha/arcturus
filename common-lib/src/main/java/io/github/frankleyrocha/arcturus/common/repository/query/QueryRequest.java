package io.github.frankleyrocha.arcturus.common.repository.query;

import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PageRequest;

public record QueryRequest(
    FilterRequest filter,
    PageRequest pageRequest
) {

    public QueryRequest {

        if(filter == null) filter = new FilterRequest();
        if(pageRequest == null) pageRequest = new PageRequest();

    }

    public QueryRequest(FilterRequest filter){
        this(filter,null);
    }

    public QueryRequest(PageRequest pageRequest){
        this(null,pageRequest);
    }

    public QueryRequest(){
        this(null,null);
    }

}
