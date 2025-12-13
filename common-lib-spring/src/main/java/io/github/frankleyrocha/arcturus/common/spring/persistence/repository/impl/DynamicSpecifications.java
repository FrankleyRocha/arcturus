package io.github.frankleyrocha.arcturus.common.spring.persistence.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.FilterAgregator;
import io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.FilterRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class DynamicSpecifications {

    public static Sort sortFromStringList(List<String> sortByStringList) {

        return Sort.by(
                sortByStringList.stream().map(sortBy -> {

                    String[] split = sortBy.split("[,:\\s]+");
                    String orderField = split[0].trim();
                    Sort.Direction direction = split.length == 2
                            && split[1].trim().equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

                    return new Sort.Order(direction, orderField);
                }).toList());

    }

    private static Field getFieldRecursive(Class entityClass, String fieldName) {
        try {
            String[] parts = fieldName.split("\\.");
            Class<?> currentClass = entityClass;

            Field field = null;

            for (String part : parts) {
                field = currentClass.getDeclaredField(part);
                currentClass = field.getType();
            }

            return field;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Campo não encontrado: " + fieldName);
        }
    }

    private static Path<?> buildPathRecursive(Path<?> root, String fieldPath) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = root;
        for (String part : parts) {

            boolean isLast = part.equals(parts[parts.length - 1]);

            if (isLast) {
                path = path.get(part); // campo simples
            } else if (path instanceof From<?, ?> from) {
                path = from.join(part, JoinType.INNER); // join dinâmico
            } else {
                throw new IllegalArgumentException("Não foi possível realizar join em: " + fieldPath);
            }
        }
        return path;
    }

    public static <T> Specification<T> fromFilters(FilterRequest filter, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> {

            Predicate[] predicates = filter.conditions().stream().filter(c -> c.filter() != null).map(cond -> {

                Field field = getFieldRecursive(entityClass, cond.path());

                Path<?> path = buildPathRecursive(root, cond.path());

                Object value = convertValue(
                        cond.filter(),
                        field.getType());

                switch (cond.op()) {
                    case EQ:
                        return criteriaBuilder.equal(path, value);

                    case LIKE:
                        if (value instanceof String string)
                            return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)),
                                    "%" + string.toLowerCase() + "%");
                        throw new IllegalArgumentException("LIKE requires a String value");

                    case GT:
                        if (value instanceof Number number) {
                            return criteriaBuilder.gt(path.as(Number.class), number);
                        } else if (value instanceof Comparable<?> comparable) {
                            return criteriaBuilder.greaterThan(path.as(Comparable.class), (Comparable) comparable);
                        }
                        throw new IllegalArgumentException("GT requires a Number or Comparable value");

                    case GTE:
                        if (value instanceof Number number) {
                            return criteriaBuilder.ge(path.as(Number.class), number);
                        } else if (value instanceof Comparable<?> comparable) {
                            return criteriaBuilder.greaterThanOrEqualTo(path.as(Comparable.class),
                                    (Comparable) comparable);
                        }
                        throw new IllegalArgumentException("GTE requires a Number or Comparable value");

                    case LT:
                        if (value instanceof Number number) {
                            return criteriaBuilder.lt(path.as(Number.class), number);
                        } else if (value instanceof Comparable<?> comparable) {
                            return criteriaBuilder.lessThan(path.as(Comparable.class), (Comparable) comparable);
                        }
                        throw new IllegalArgumentException("LT requires a Number or Comparable value");

                    case LTE:
                        if (value instanceof Number number) {
                            return criteriaBuilder.le(path.as(Number.class), number);
                        } else if (value instanceof Comparable<?> comparable) {
                            return criteriaBuilder.lessThanOrEqualTo(path.as(Comparable.class),
                                    (Comparable) comparable);
                        }
                        throw new IllegalArgumentException("LT requires a Number or Comparable value");

                    case IN:
                        if (value instanceof Collection<?> collection) {
                            return path.in(collection);
                        }
                        throw new IllegalArgumentException("IN requires a Collection or array value");

                    default:
                        throw new IllegalArgumentException("Operator not supported");
                }

            }).toArray(Predicate[]::new);

            if (predicates.length == 0)
                return null;

            if (predicates.length == 1)
                return predicates[0];

            if (filter.agregator().equals(FilterAgregator.AND))
                return criteriaBuilder.and(predicates);
            else
                return criteriaBuilder.or(predicates);
        };
    }

    private static Object convertValue(Object value, Class<?> fieldType) {

        if (value == null)
            return null;

        if (value instanceof Collection<?> collection)
            return collection.stream().map(o -> convertValue(o, fieldType)).toList();

        if (fieldType.isEnum()) {
            return Enum.valueOf((Class<Enum>) fieldType, value.toString());
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (fieldType.equals(LocalDate.class)) {
            return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_DATE);
        } else if (fieldType.equals(UUID.class)) {
            return UUID.fromString(value.toString());
        } else if (fieldType.equals(String.class)) {
            return value;
        } else if (fieldType.isAnnotationPresent(Entity.class)) {

            if (value instanceof Map) {
                try {

                    Map<String, Object> fieldsMap = (Map) value;
                    Object instance = fieldType.getConstructor().newInstance();

                    for (String fieldName : fieldsMap.keySet()) {

                        Field field = fieldType.getDeclaredField(fieldName);
                        String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                        Method setter = fieldType.getMethod(setterName, field.getType());

                        Object fieldValue = convertValue(fieldsMap.get(fieldName), field.getType());

                        setter.invoke(instance, fieldValue);

                    }

                    return instance;

                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Erro ao construir objeto para a classe: " + fieldType.getName());
                }

            }

        }

        throw new IllegalArgumentException("Tipo de campo não suportado: " + fieldType);
    }
}