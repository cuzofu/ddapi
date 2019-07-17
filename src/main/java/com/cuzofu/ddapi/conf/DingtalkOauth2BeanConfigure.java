package com.cuzofu.ddapi.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cuzofu.ddapi.auth2.DefaultDingtalkOAuth2AccessToken;
import com.cuzofu.ddapi.auth2.DefaultDingtalkToken;
import com.cuzofu.ddapi.auth2.DingtalkOAuth2AccessToken;
import com.cuzofu.ddapi.auth2.DingtalkToken;
import com.cuzofu.ddapi.properties.DingtalkProperties;

@Configuration
public class DingtalkOauth2BeanConfigure {

	@Autowired
	private DingtalkProperties dingtalkProperties;

	@Autowired
	private DingtalkToken dingtalkToken;

	@Bean
	@ConditionalOnMissingBean(DingtalkToken.class)
	public DingtalkToken dingtalkToken() {
		DingtalkToken dingtalkToken = new DefaultDingtalkToken(dingtalkProperties.getApi().getAccessToken(),
				dingtalkProperties.getCorp().getAppKey(), dingtalkProperties.getCorp().getAppSecret());
		return dingtalkToken;
	}

	@Bean
	@ConditionalOnMissingBean(DingtalkOAuth2AccessToken.class)
	public DingtalkOAuth2AccessToken dingtalkOAuth2AccessToken() {
		DingtalkOAuth2AccessToken dingtalkOAuth2AccessToken = new DefaultDingtalkOAuth2AccessToken(dingtalkToken);
		return dingtalkOAuth2AccessToken;
	}

}
