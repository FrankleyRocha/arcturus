package io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter;

public interface FilterValueConverter<FROM,TO> {

    TO from(FROM from);

}
