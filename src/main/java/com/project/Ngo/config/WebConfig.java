package com.project.Ngo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Correctly override addResourceHandlers method
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/gallery/**")
                .addResourceLocations("file:D:/java_program/project/Ngo_Backend/Ngo_Backend/uploads/gallery/");
    }

    // CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins("http://localhost:5173") // Allow requests from your React app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specified HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials (cookies, etc.)
            }
        };
    }

}
