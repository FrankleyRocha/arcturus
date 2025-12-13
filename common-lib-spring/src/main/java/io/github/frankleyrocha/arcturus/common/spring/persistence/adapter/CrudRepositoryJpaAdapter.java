package io.github.frankleyrocha.arcturus.common.spring.persistence.adapter;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.frankleyrocha.arcturus.common.mapper.EntityMapper;
import io.github.frankleyrocha.arcturus.common.repository.CrudRepository;
import io.github.frankleyrocha.arcturus.common.repository.query.QueryRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PagedResponse;
import io.github.frankleyrocha.arcturus.common.spring.persistence.DynamicSpecifications;
import jakarta.transaction.Transactional;

public abstract class CrudRepositoryJpaAdapter<T, ENTITY, ID> implements CrudRepository<T, ID> {

    private final JpaRepository<ENTITY, ID> repo;
    private final JpaSpecificationExecutor<ENTITY> repoSpecificationExecutor;
    private final EntityMapper<ENTITY, T> mapper;

    // private final Class<T> domainClass;
    private final Class<ENTITY> entityClass;

    public CrudRepositoryJpaAdapter(
            JpaRepository<ENTITY, ID> repo,
            EntityMapper<ENTITY, T> mapper) {
        this.repo = repo;
        this.repoSpecificationExecutor = (JpaSpecificationExecutor<ENTITY>) repo;
        this.mapper = mapper;

        // Recupera o tipo genérico da subclasse concreta
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();

        // this.domainClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.entityClass = (Class<ENTITY>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Transactional
    public T save(T domainEntity) {

        ENTITY savedEntity = repo.save(
                mapper.toEntity(domainEntity));

        return mapper.fromEntity(savedEntity);
    }

    @Transactional
    public List<T> saveAll(List<T> domainEntities) {

        List<ENTITY> savedEntities = repo.saveAll(
                domainEntities.stream().map(mapper::toEntity).toList());

        return savedEntities.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public Optional<T> findById(ID id) {

        return repo.findById(id).map(mapper::fromEntity);

    }

    @Transactional
    public List<T> findAll() {

        return repo.findAll().stream().map(mapper::fromEntity).toList();

    }

    @Transactional
    public Optional<T> deleteById(ID id) {

        Optional<ENTITY> optionalEntity = repo.findById(id);

        optionalEntity.ifPresent(foundEntity -> repo.delete(foundEntity));

        return optionalEntity.map(mapper::fromEntity);

    }

    @Transactional
    public Optional<T> findOne(FilterRequest filter) {

        Specification<ENTITY> spec = DynamicSpecifications.fromFilters(filter, entityClass);
        return repoSpecificationExecutor.findOne(spec).map(mapper::fromEntity);
    }

    @Transactional
    public List<T> findAll(List<String> sortByList) {

        Sort sort = DynamicSpecifications.sortFromStringList(sortByList);

        return repoSpecificationExecutor.findAll(null, sort).stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public List<T> findAll(FilterRequest filter, List<String> sortByList) {

        Sort sort = DynamicSpecifications.sortFromStringList(sortByList);

        Specification<ENTITY> spec = DynamicSpecifications.fromFilters(filter, entityClass);
        return repoSpecificationExecutor.findAll(spec, sort).stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public List<T> findAll(FilterRequest filter) {

        Specification<ENTITY> spec = DynamicSpecifications.fromFilters(filter, entityClass);
        return repoSpecificationExecutor.findAll(spec).stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public PagedResponse<T> findAll(QueryRequest queryRequest) {

        Sort sort = DynamicSpecifications.sortFromStringList(queryRequest.pageRequest().sortBy());

        Pageable page = PageRequest.of(
                queryRequest.pageRequest().pageNumber(),
                queryRequest.pageRequest().pageSize(),
                sort);

        Specification<ENTITY> spec = DynamicSpecifications.fromFilters(queryRequest.filter(), entityClass);

        Page<ENTITY> pagedEntities = repoSpecificationExecutor.findAll(spec, page);

        PagedResponse<T> resp = new PagedResponse<>(
                pagedEntities.map(mapper::fromEntity).toList(),
                pagedEntities.getNumber(),
                pagedEntities.getSize(),
                pagedEntities.getTotalElements());

        return resp;

    }

}
