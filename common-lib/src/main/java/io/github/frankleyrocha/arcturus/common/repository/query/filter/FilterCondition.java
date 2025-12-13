package io.github.frankleyrocha.arcturus.common.repository.query.filter;

import java.util.Collection;

public record FilterCondition<T>(
    String path,
    Boolean negate,
    FilterOperator op,
    Boolean ignoreCase,
    T filter
) {

    public FilterCondition {
        if(negate == null) negate = false; // padrão: sem negação

        if(op == null){
            if(filter instanceof String)
                op = FilterOperator.LIKE; // padrão: String -> LIKE
            else if(filter instanceof Collection)
                op = FilterOperator.IN; // padrão: Collection -> IN
            else
                op = FilterOperator.EQ; // padrão: EQ
        }

        if(ignoreCase == null){
            if(op.equals(FilterOperator.LIKE))
                ignoreCase = true; // padrão: LIKE -> ignoreCase = true
            else
                ignoreCase = false;
        }
    }

    public FilterCondition(String path, FilterOperator operator, T filter) {
        this(path,null,operator,null, filter);
    }

    public FilterCondition(String path, T filter) {
        this(path,null, null,null,filter);
    }

}
