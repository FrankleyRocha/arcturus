package io.github.frankleyrocha.arcturus.common.repository.query.filter.field;

public class FilterField<T> extends BaseFilterField<T> {

    private T filter;

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

}
