package com.sage.demo0809.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Created by Sage on 2017/3/14.
 */
@Retention(RetentionPolicy.CLASS)
@Target({PARAMETER,LOCAL_VARIABLE,METHOD,FIELD,ANNOTATION_TYPE})

public @interface Test {
    long  value() default -1;
    long  odd() default 1;
    long even() default 2;

}
