package io.github.frankleyrocha.arcturus.common.persistence.repository.query.page;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    Integer pageNumber,
    Integer pageSize,
    Long totalElements,
    Integer totalPages,
    Boolean hasNext,
    Boolean hasPrevious
) {

    public PagedResponse {
        if( totalPages == null) totalPages = (int) Math.ceil((double) totalElements / pageSize);
        if( hasNext == null) hasNext = pageNumber < totalPages - 1;
        if( hasPrevious == null) hasPrevious = pageNumber > 0;
    }

    public PagedResponse(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Integer totalPages
    ){
        this(content, pageNumber, pageSize, totalPages.longValue()*pageSize, null, null, null);
    }

    public PagedResponse(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements
    ){
        this(content, pageNumber, pageSize, totalElements, null, null, null);
    }

}
