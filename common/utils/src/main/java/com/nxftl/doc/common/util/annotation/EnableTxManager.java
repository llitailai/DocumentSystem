package com.nxftl.doc.common.util.annotation;

import com.nxftl.doc.common.util.config.TxDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/29 9:09
 * @discription
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TxDefinitionRegistrar.class)
public @interface EnableTxManager {

    /**
     * 切点,默认是medbanks 下所有service
     */
    String[] pointcut() default {"* cn.xxx..service..*(..)"};

    /**
     * 超时时间
     */
    int txMethodTimeOut() default 10;
}
