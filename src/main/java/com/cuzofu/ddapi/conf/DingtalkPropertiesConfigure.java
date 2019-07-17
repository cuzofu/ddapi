package com.cuzofu.ddapi.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cuzofu.ddapi.properties.DingtalkProperties;

@Configuration
@EnableConfigurationProperties(DingtalkProperties.class)
public class DingtalkPropertiesConfigure {
}