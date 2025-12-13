package io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.field.BaseFilterField;
import io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.field.FilterField;
import io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.field.ListFilterField;

public abstract class AbstractFilter {

    private FilterAgregator agregator;

    public FilterAgregator getAgregator() {
        return agregator;
    }

    public void setAgregator(FilterAgregator agregator) {
        this.agregator = agregator;
    }

    public FilterRequest toDTO() {
        List<FilterCondition<?>> filterFields = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> BaseFilterField.class.isAssignableFrom(field.getType()))
                .map(this::toFilterCondition)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList<FilterCondition<?>>::new));

        return new FilterRequest(agregator, filterFields);
    }

    private FilterCondition<?> toFilterCondition(Field field) {

        field.setAccessible(true);
        try {
            BaseFilterField<?> filterField = (BaseFilterField<?>) field.get(this);

            if (filterField == null)
                return null;

            String path = field.isAnnotationPresent(FilterPath.class) && !field.getAnnotation(FilterPath.class).value().isBlank() ?
                field.getAnnotation(FilterPath.class).value() :
                field.getName();

            Object filter = null;

            if (FilterField.class.isAssignableFrom(field.getType()))
                filter = ((FilterField<?>) filterField).getFilter();
            else
                filter = ((ListFilterField<?>) filterField).getFilter();


            if (filter == null)
                return null;

            Optional<FilterValueConverter<Object,Object>> converterOpt =
                getConverter(field);

            return new FilterCondition<>(
                path,
                filterField.getNegate(),
                filterField.getOp(),
                filterField.getIgnoreCase(),
                converterOpt.isPresent() ? converterOpt.get().from(filter) : filter
            );

        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar campo de filtro: " + field.getName(), e);
        }

    }

    @SuppressWarnings("unchecked")
    private <FROM, TO> Optional<FilterValueConverter<FROM, TO>> getConverter(Field field) {

        FilterPath annotation = field.getAnnotation(FilterPath.class);
        if(annotation == null) return Optional.empty();

        Class<? extends FilterValueConverter<?, ?>> converterClass = annotation.converterClass();
        if (converterClass == NoConverter.class) return Optional.empty();

        try {
            FilterValueConverter<?, ?> instance = converterClass.getDeclaredConstructor().newInstance();
            return Optional.of((FilterValueConverter<FROM, TO>) instance);
        } catch (Exception e) {
            throw new RuntimeException(
                "Não foi possível instanciar o converter: " + converterClass.getName(), e);
        }
    }

}
