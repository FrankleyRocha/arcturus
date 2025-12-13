package io.github.frankleyrocha.arcturus.mapito;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Mapito {

    private static final ObjectMapper mapper = new ObjectMapper();

    private Mapito() {
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {
        if (map == null)
            return Collections.emptyMap();

        Map<String, Object> result = new LinkedHashMap<>();
        map.forEach((k, v) -> flattenEntry(k, v, "", result));
        return result;
    }

    private static void flattenEntry(String key, Object value, String prefix, Map<String, Object> result) {
        String fullKey = prefix.isEmpty() ? key : prefix + "." + key;

        if (value instanceof Map<?, ?> innerMap) {
            @SuppressWarnings("unchecked")
            Map<String, Object> typedMap = (Map<String, Object>) innerMap;
            typedMap.forEach((k, v) -> flattenEntry(k, v, fullKey, result));
        } else {
            result.put(fullKey, value);
        }
    }

    public static class Builder<T> {
        private Class<T> clazz;
        private Map<String, Object> map;

        public Builder(Class<T> clazz) {
            this.clazz = clazz;
            this.map = new HashMap<>();
        }

        public Builder(Class<T> clazz, Map<String, Object> map) {
            this.clazz = clazz;
            this.map = map;
        }

        public Builder<T> with(String key, Object value){

            map.put(key, value);

            return this;
        }

        public T build(){
            return fromMap(clazz, map);
        }

    }

    public static <T> Builder<T> getBuilder(Class<T> clazz){
        return new Builder<T>(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> Builder<T> toBuilder(T obj){

        Class<?> clazz = obj.getClass();
        Map<String, Object> map = toMap(obj);

        return new Builder<T>((Class<T>) clazz,map);

    }

    public static <T> T fromMap(Class<T> clazz, Map<String, Object> map) {
        return mapper.convertValue(map, clazz);
    }

    public static Map<String, Object> toMap(Object obj) {
        return mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    public static Map<String, Object> toFlatMap(Object obj) {
        return flatten(toMap(obj));
    }

    @SuppressWarnings("unchecked")
    public static <T> T merge(T target, T source) {
        if (target == null || source == null) {
            throw new IllegalArgumentException("Neither target nor source can be null");
        }

        Class<?> clazz = target.getClass();

        Map<String, Object> targetMap = toMap(target);
        Map<String, Object> sourceMap = toMap(source);

        for (var entry : sourceMap.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                targetMap.put(entry.getKey(), value);
            }
        }

        return fromMap((Class<T>) clazz, targetMap);

    }

}
