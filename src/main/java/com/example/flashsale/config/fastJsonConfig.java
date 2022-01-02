package com.example.flashsale.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration(exclude = {
        JmxAutoConfiguration.class
})
public class fastJsonConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // Contains null in map
                SerializerFeature.WriteMapNullValue,
                // Convert String null to ""
                SerializerFeature.WriteNullStringAsEmpty,
                // Convert Number null to 0
                SerializerFeature.WriteNullNumberAsZero,
                // Convert List null to []
                SerializerFeature.WriteNullListAsEmpty,
                // Convert Boolean null to false
                SerializerFeature.WriteNullBooleanAsFalse,
                // DisableCircularReference
                SerializerFeature.DisableCircularReferenceDetect
        );

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypesList = new ArrayList<>();

        // Resolve Chinese Characters problems
        mediaTypesList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypesList);
        converters.add(converter);
    }
}
