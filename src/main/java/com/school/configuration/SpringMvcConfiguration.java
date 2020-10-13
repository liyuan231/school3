//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {
    @Value("${file.path}")
    private String filePath;
    @Value("${spring.file.path}")
    private String springFilePath;

    public SpringMvcConfiguration() {
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{this.springFilePath + "**"}).addResourceLocations(new String[]{"file:" + this.filePath}).setCachePeriod(31556926);
    }
}
