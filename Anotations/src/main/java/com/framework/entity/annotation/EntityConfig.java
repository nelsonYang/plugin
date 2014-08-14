package com.framework.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author nelson
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityConfig {
    String entityName();
    String[] keyFields();
    boolean useCache() default false; 
    long maxElementsInMemory() default 2000;
    long timeToIdleSeconds() default 3000;
    long timeToLiveSeconds() default 3000;
}
