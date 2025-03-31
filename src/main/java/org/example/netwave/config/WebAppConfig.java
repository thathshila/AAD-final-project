package org.example.netwave.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Dynamically get the absolute path for the uploads directory
        String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
