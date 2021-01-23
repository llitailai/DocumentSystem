package com.nxftl.doc.common.annotation;

import java.lang.annotation.*;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/22 11:49
 * @discription 不允许为null 标注该注解即可
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
    /**
     * 是否允许为null 如果允许请标注 permitNull 为 true
     * @return
     */
    public String value() default "";
}
