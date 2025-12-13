package io.github.frankleyrocha.arcturus.common.repository.query.filter;

public interface FilterValueConverter<FROM,TO> {

    TO from(FROM from);

}
