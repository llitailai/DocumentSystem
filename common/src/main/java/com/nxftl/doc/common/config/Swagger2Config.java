package com.nxftl.doc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author darkltl
 * @date 2020/12/18 11:47
 * @discription
 * @className Swagger2Config
 * @group com.alpha.crm.config
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {



    //配置在线文档的基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger接口平台")
                .description("无")
                .termsOfServiceUrl("nxftl")
                .version("1.0")
                .build();
    }


    @Bean("sysUserApi")
    public Docket createSysUserDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("sysUserApi")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nxftl.doc.sys.user"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("logDocApi")
    public Docket createLogDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("logDocApi")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nxftl.doc.sys.log"))
                .paths(PathSelectors.any())
                .build();
    }






}
