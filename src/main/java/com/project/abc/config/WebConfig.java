package com.project.abc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig {

    @Value("${file.prefix}")
    String prefix;

    @Value("${folder.path.name}")
    String folder;

    @Bean
    public WebMvcConfigurer configurer() {

        return  new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/"+ prefix +"/**").addResourceLocations(folder);
            }
        };
    }
}
