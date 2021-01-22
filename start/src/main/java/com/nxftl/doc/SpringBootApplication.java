package com.nxftl.doc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/19 15:55
 * @discription
 */
@EnableScheduling
@org.springframework.boot.autoconfigure.SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class})
@EnableSwagger2
@MapperScan("com.nxftl.doc.*.*.mapper")
@ComponentScan(basePackages = {""})
public class SpringBootApplication {
    public static void main(String[] args){
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
