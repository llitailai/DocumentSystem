package com.nxftl.doc.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/19 15:55
 * @discription
 */
@EnableScheduling
@MapperScan("com.nxftl.doc.*.*.mapper")
@org.springframework.boot.autoconfigure.SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@EnableSwagger2
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
