package com.cuzfou.ddapi.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cuzfou.ddapi.properties.DingtalkProperties;

@Configuration
@EnableConfigurationProperties(DingtalkProperties.class)
public class DingtalkPropertiesConfigure {
}