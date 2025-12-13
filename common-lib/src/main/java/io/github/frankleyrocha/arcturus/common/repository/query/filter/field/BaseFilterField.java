package io.github.frankleyrocha.arcturus.common.repository.query.filter.field;

import io.github.frankleyrocha.arcturus.common.repository.query.filter.FilterOperator;

public class BaseFilterField<T> {

    private Boolean negate;
    private FilterOperator op;
    private Boolean ignoreCase;

    public BaseFilterField() {
    }

    public BaseFilterField(Boolean negate, FilterOperator op, Boolean ignoreCase) {
        this.negate = negate;
        this.op = op;
        this.ignoreCase = ignoreCase;
    }

    public Boolean getNegate() {
        return negate;
    }

    public void setNegate(Boolean negate) {
        this.negate = negate;
    }

    public FilterOperator getOp() {
        return op;
    }

    public void setOp(FilterOperator op) {
        this.op = op;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

}
