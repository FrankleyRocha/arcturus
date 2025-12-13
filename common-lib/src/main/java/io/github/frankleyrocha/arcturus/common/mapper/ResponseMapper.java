package io.github.frankleyrocha.arcturus.common.mapper;

public interface ResponseMapper <RESPONSE, T>{

    RESPONSE toResponse(T obj);

}
