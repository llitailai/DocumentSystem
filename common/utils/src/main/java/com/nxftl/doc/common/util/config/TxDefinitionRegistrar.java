package com.nxftl.doc.common.util.config;

import com.google.common.base.Joiner;
import com.nxftl.doc.common.util.annotation.EnableTxManager;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/29 9:11
 * @discription
 */
public class TxDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableTxManager.class.getName());
        assert attributes != null;
        String pointcut = getPointcut(attributes);
        int txMethodTimeOut = getTxMethodTimeOut(attributes);

        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(GlobalTransactionConfig.class);
        definition.addPropertyValue("pointcut", pointcut);
        definition.addPropertyValue("txMethodTimeOut", txMethodTimeOut);
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        String beanName = StringUtils.uncapitalize(GlobalTransactionConfig.class.getSimpleName());
        registry.registerBeanDefinition(beanName, beanDefinition);

    }

    private String getPointcut(Map<String, Object> attributes) {
        Set<String> pointcut = new HashSet<>();
        for (String pc : (String[]) attributes.get("pointcut")) {
            if (StringUtils.hasText(pc)) {
                pointcut.add("execution(" + pc + " )");
            }
        }
        return Joiner.on("||").join(pointcut);
    }

    private int getTxMethodTimeOut(Map<String, Object> attributes) {
        return (int) attributes.get("txMethodTimeOut");
    }
}
