package com.transportersInc.test.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api.database.sp")
@Data
public class Database {

    private String consultContainers;
    private String insertStats;
    private String consultStats;

}
