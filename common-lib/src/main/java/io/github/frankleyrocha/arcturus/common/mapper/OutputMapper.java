package io.github.frankleyrocha.arcturus.common.mapper;

public interface OutputMapper <OUTPUT, T>{

    OUTPUT toOutput(T obj);

}
