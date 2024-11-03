package com.project.Ngo.DTO;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession // Enable Redis-based session management
public class RedisConfig {
    // Additional Redis configuration can go here if needed
}
