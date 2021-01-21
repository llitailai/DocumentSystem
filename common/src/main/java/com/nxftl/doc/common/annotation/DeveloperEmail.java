package com.nxftl.doc.common.annotation;

import java.lang.annotation.*;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/21 16:48
 * @discription 自定义 email注解 服务于日志发送于邮箱功能
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeveloperEmail {

    String email() default "";

}
