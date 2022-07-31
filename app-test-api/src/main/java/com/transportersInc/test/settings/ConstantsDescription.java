package com.transportersInc.test.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api.constants")
@Data
public class ConstantsDescription {

    private String successful;
    private String budget;
    private String repeated;
    private String dispatched;
    private String errorExceptionService;

}
