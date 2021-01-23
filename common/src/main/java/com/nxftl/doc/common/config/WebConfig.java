package com.nxftl.doc.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/21 16:02
 * @discription web定制化配置
 */
@Configuration

public class WebConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*
            先将jackson转换器删除,否则匹配不到fastjson
         */
        for(int i=converters.size()-1;i>=0;i--){
            if(converters.get(i) instanceof MappingJackson2HttpMessageConverter){
                converters.remove(i);
            }
        }

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue, //是否输出值为null的字段
                SerializerFeature.WriteNullListAsEmpty, // 将Collection类型字段的空值输出为[]
                SerializerFeature.WriteNullStringAsEmpty, //将字符串类型的空值输出为空串
                SerializerFeature.WriteNullNumberAsZero, //将数值类型的空值输出为0
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect //禁用循环引用
        );

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(0,fastJsonHttpMessageConverter);
    }
}
