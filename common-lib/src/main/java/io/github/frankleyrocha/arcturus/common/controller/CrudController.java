package io.github.frankleyrocha.arcturus.common.controller;

import java.util.List;

import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterAgregator;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PageRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PagedResponse;

public interface CrudController <T, ID/*, FILTER extends AbstractFilter*/>{

    T create(T obj);
    T get(ID id);
    List<T> list();
    //PagedResponseDTO<DTO> find(FILTER filter, PageRequestDTO page);
    PagedResponse<T> find(T filter, FilterAgregator agregator, PageRequest page);
    T update(ID id, T obj);
    void delete(ID id);

}