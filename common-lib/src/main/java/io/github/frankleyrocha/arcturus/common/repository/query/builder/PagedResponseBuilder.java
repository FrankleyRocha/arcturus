package io.github.frankleyrocha.arcturus.common.repository.query.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.github.frankleyrocha.arcturus.common.repository.query.page.PagedResponse;

public class PagedResponseBuilder<T> {

    private List<T> content = new ArrayList<>();
    private Integer page = 0;
    private Integer size = 20;
    private Long totalElements = null;
    private Integer totalPages = null;
    private Boolean hasNext = null;
    private Boolean hasPrevious = null;

    public PagedResponseBuilder<T> from(PagedResponse<T> dto) {
        if (dto == null)
            return this;

        this.content = new ArrayList<>(dto.content());
        this.page = dto.pageNumber();
        this.size = dto.pageSize();
        this.totalElements = dto.totalElements();
        this.totalPages = dto.totalPages();
        this.hasNext = dto.hasNext();
        this.hasPrevious = dto.hasPrevious();

        return this;
    }

    public <R> PagedResponseBuilder<R> mapTo(Function<? super T, ? extends R> mapper) {
        PagedResponseBuilder<R> newBuilder = new PagedResponseBuilder<>();
        newBuilder.content = this.content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        newBuilder.page = this.page;
        newBuilder.size = this.size;
        newBuilder.totalElements = this.totalElements;
        newBuilder.totalPages = this.totalPages;
        newBuilder.hasNext = this.hasNext;
        newBuilder.hasPrevious = this.hasPrevious;
        return newBuilder;
    }

    public <S> PagedResponseBuilder<T> mapFrom(PagedResponse<S> dto, Function<? super S, ? extends T> mapper) {
        if (dto == null)
            return this;

        this.content = dto.content().stream()
                .map(mapper)
                .collect(Collectors.toList());
        this.page = dto.pageNumber();
        this.size = dto.pageSize();
        this.totalElements = dto.totalElements();
        this.totalPages = dto.totalPages();
        this.hasNext = dto.hasNext();
        this.hasPrevious = dto.hasPrevious();

        return this;
    }

    public PagedResponseBuilder<T> content(List<T> content) {
        this.content = content;
        return this;
    }

    public PagedResponseBuilder<T> page(Integer page) {
        this.page = page;
        return this;
    }

    public PagedResponseBuilder<T> size(Integer size) {
        this.size = size;
        return this;
    }

    public PagedResponseBuilder<T> totalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public PagedResponseBuilder<T> totalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PagedResponseBuilder<T> hasNext(Boolean hasNext) {
        this.hasNext = hasNext;
        return this;
    }

    public PagedResponseBuilder<T> hasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
        return this;
    }

    public PagedResponse<T> build() {
        if (totalPages == null && totalElements != null && size != null && size > 0) {
            totalPages = (int) Math.ceil((double) totalElements / size);
        }

        if (totalElements == null && totalPages != null && size != null) {
            totalElements = (long) totalPages * size;
        }

        if (hasNext == null && totalPages != null && page != null) {
            hasNext = page < totalPages - 1;
        }
        if (hasPrevious == null && page != null) {
            hasPrevious = page > 0;
        }

        return new PagedResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages,
                hasNext,
                hasPrevious);
    }
}
