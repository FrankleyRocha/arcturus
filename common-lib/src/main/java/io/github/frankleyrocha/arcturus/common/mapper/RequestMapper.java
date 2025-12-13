package io.github.frankleyrocha.arcturus.common.mapper;

public interface RequestMapper <REQUEST, T>{

    T fromRequest(REQUEST input);

}
