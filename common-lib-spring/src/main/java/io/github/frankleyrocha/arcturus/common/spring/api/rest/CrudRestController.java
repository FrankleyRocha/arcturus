package io.github.frankleyrocha.arcturus.common.spring.api.rest;

import java.util.List;
import java.util.Map;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.frankleyrocha.arcturus.common.controller.CrudController;
import io.github.frankleyrocha.arcturus.common.repository.CrudRepository;
import io.github.frankleyrocha.arcturus.common.repository.query.QueryRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.builder.QueryRequestBuilder;
import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterAgregator;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PageRequest;
import io.github.frankleyrocha.arcturus.common.repository.query.page.PagedResponse;
import io.github.frankleyrocha.arcturus.mapito.Mapito;
import jakarta.persistence.EntityNotFoundException;

public abstract class CrudRestController<T, ID /* , FILTER extends AbstractFilter */>
        implements CrudController<T, ID/* , FILTER */> {

    protected final CrudRepository<T, ID> service;
    // private final Class<T> dtoClass;

    // @SuppressWarnings("unchecked")
    public CrudRestController(CrudRepository<T, ID> service) {

        this.service = service;

        // ParameterizedType genericSuperclass = (ParameterizedType)
        // getClass().getGenericSuperclass();
        // this.dtoClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @PostMapping
    public T create(@RequestBody T createDto) {

        return service.save(
                Mapito.toBuilder(createDto)
                        .with("id", null)
                        .build());

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {

        service.deleteById(id).orElseThrow(EntityNotFoundException::new);

    }

    @GetMapping("/query")
    public PagedResponse<T> find(
            @ParameterObject T filter,
            @RequestParam(defaultValue = "AND") FilterAgregator agregator,
            @ParameterObject PageRequest page) {

        QueryRequestBuilder builder = new QueryRequestBuilder()
                .agregator(agregator)
                .page(page);

        Map<String, Object> filterMap = Mapito.toFlatMap(filter);

        for (String key : filterMap.keySet()) {
            Object value = filterMap.get(key);
            builder.addConditionIf(value != null, c -> c.path(key).filter(value));
        }

        QueryRequest queryRequest = builder.build();

        return service.findAll(queryRequest);

    }

    @GetMapping("/{id}")
    public T get(@PathVariable ID id) {

        return service.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public List<T> list() {

        return service.findAll();
    }

    @PatchMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T updateDto) {

        T foundDto = service.findById(id).orElseThrow(EntityNotFoundException::new);

        T mergedDto = Mapito.merge(foundDto, updateDto);

        return service.save(mergedDto);
    }

}
