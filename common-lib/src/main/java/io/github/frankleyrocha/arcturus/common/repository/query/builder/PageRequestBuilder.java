package io.github.frankleyrocha.arcturus.common.repository.query.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.frankleyrocha.arcturus.common.repository.query.page.PageRequest;

public class PageRequestBuilder {
    private Integer pageNumber;
    private Integer pageSize;
    private List<String> sortBy = new ArrayList<>();

    public PageRequestBuilder pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PageRequestBuilder pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageRequestBuilder sortBy(String ...sortBy) {
        this.sortBy = Arrays.asList(sortBy);
        return this;
    }


    public PageRequest build() {
        return new PageRequest(pageNumber, pageSize, sortBy);
    }
}
