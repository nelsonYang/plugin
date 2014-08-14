package com.framework.entity.annotation;

import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.FilterTypeEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author nelson
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldConfig {
    String fieldName();
    FieldTypeEnum fieldType();
    String defaultValue() default "";
    String description() default "";
    FilterTypeEnum[] filter() default {FilterTypeEnum.ESCAPE,FilterTypeEnum.SECURITY};
}
