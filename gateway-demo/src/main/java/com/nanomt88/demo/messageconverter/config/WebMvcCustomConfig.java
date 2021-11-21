package com.nanomt88.demo.messageconverter.config;

import com.nanomt88.demo.messageconverter.http.HttpPropertiesMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author nanomt88@gmail.com
 * @create 2019-07-28 10:17
 **/
@Configuration
public class WebMvcCustomConfig implements WebMvcConfigurer {

    /**
     * spring bug导致 设置了之后不生效，会被清空后设置为默认值
     * @param converters
     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(0, new MappingJackson2HttpMessageConverter());
//    }

    /**
     * 扩展 http消息 解析类
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new HttpPropertiesMessageConverter());
        System.err.println("extendMessageConverters invoke ::::: " + converters) ;
    }
}
