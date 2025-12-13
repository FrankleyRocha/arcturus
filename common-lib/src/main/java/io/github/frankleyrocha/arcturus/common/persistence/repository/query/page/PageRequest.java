package io.github.frankleyrocha.arcturus.common.persistence.repository.query.page;

import java.util.ArrayList;
import java.util.List;

public record PageRequest(
    Integer pageNumber,
    Integer pageSize,
    List<String> sortBy
) {

    public PageRequest {

        if(pageNumber == null) pageNumber = 0;
        if(pageSize == null) pageSize = 20;
        if(sortBy == null){
            sortBy = new ArrayList<>();
            sortBy.add("id,asc");
        }

    }

    public PageRequest(){
        this(null,null,null);
    }

}
