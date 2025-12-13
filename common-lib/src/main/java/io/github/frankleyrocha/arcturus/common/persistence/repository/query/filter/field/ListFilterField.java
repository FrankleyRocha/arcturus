package io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter.field;

import java.util.List;

public class ListFilterField<T> extends BaseFilterField<T>{

    private List<T> filter;

    public List<T> getFilter() {
        return filter;
    }

    public void setFilter(List<T> filter) {
        this.filter = filter;
    }

}
