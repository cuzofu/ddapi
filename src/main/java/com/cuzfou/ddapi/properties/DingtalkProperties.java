package com.cuzfou.ddapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "dingtalk")
@Data
public class DingtalkProperties {

	/**
	 * 企业信息
	 */
	private CorpProperties corp = new CorpProperties();

	/**
	 * 钉钉API接口配置类
	 */
	private ApiProperties api = new ApiProperties();

}
