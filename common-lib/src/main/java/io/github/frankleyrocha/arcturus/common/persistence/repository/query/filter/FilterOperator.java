package io.github.frankleyrocha.arcturus.common.persistence.repository.query.filter;

public enum FilterOperator {

    EQ,   // igual
    GT,   // maior que
    LT,   // menor que
    GTE,  // maior ou igual
    LTE,  // menor ou igual
    LIKE, // correspondência parcial (ex.: SQL LIKE)
    IN    // pertence a uma lista de valores

}