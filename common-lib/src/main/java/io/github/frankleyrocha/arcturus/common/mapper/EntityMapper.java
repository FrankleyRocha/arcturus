package io.github.frankleyrocha.arcturus.common.mapper;

public interface EntityMapper <ENTITY,T>{

    T fromEntity(ENTITY entity);
    ENTITY toEntity(T obj);

}
