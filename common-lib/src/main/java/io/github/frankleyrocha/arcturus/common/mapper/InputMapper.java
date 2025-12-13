package io.github.frankleyrocha.arcturus.common.mapper;

public interface InputMapper <INPUT, T>{

    T fromInput(INPUT input);

}
