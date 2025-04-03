package com.javarush.lapkinu.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/.env", factory = DotenvPropertySourceFactory.class)
public class EnvConfig {
}
