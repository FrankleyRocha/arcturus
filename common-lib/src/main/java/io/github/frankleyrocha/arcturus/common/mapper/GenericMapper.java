package io.github.frankleyrocha.arcturus.common.mapper;

public interface GenericMapper <INPUT,OUTPUT>{

    OUTPUT from(INPUT input);
    INPUT to(OUTPUT output);

}
