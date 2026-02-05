package com.example.demo.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "storage")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ConfigProperties {
    String scheduleStorage;
    String nameJsonFile;
}
