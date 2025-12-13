package io.github.frankleyrocha.arcturus.common.repository;

import java.util.List;
import java.util.Optional;

import io.github.frankleyrocha.arcturus.common.repository.query.QueryRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PagedResponse;

public interface CrudRepository<T, ID> {

    Optional<T> findById(ID id);
    Optional<T> findOne(FilterRequest filter);
    T save(T domainEntity);
    List<T> saveAll(List<T> domainEntities);
    Optional<T> deleteById(ID id);
    List<T> findAll();
    List<T> findAll(List<String> sortBy);
    List<T> findAll(FilterRequest filter);
    List<T> findAll(FilterRequest filter, List<String> sortBy);
    PagedResponse<T> findAll(QueryRequest queryRequest);

}