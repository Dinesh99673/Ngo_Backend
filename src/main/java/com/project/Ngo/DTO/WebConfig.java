package com.project.Ngo.DTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

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
