package io.github.frankleyrocha.arcturus.common.repository.query.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
public @interface FilterPath {

    String value() default "";

    Class<? extends FilterValueConverter<?, ?>> converterClass() default NoConverter.class;

}

class NoConverter implements FilterValueConverter<Object, Object> {
    @Override
    public Object from(Object from) { return from; }
}