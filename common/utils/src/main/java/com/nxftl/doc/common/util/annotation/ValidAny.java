package com.nxftl.doc.common.util.annotation;

import java.lang.annotation.*;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 14:53
 * @discription
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAny {

    public boolean email() default false;

    public boolean tel() default false;

    public boolean password() default false;

    public boolean isEntity() default false;

    /**
     * 如果是实体对象,isEntity为true 则如果实体内部标注该注解属性为空,抛出返回异常主体为value()信息
     * @return
     */
    public String value() default "";

    public boolean exit() default false;

    public String exitError() default "";
}
