package com.nxftl.doc.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:41
 * @discription
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredToken {
    boolean required() default true;
}
