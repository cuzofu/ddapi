package com.cuzfou.ddapi.auth2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDingtalkOAuth2AccessToken implements DingtalkOAuth2AccessToken {

	private static String ACCESS_TOKEN = "";

	public DefaultDingtalkOAuth2AccessToken(DingtalkToken dingtalkToken) {
		try {
			ACCESS_TOKEN = dingtalkToken.getToken();
			ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleWithFixedDelay(new AccessTokenRenewalTask(dingtalkToken), 60L, 60L,
					TimeUnit.SECONDS);
		} catch (ApiException e) {
			log.error("获取AccessToken失败，响应的错误信息：{}", e.getMessage());
			throw new RuntimeException(e.getErrMsg());
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public String getAccessToken() {
		return this.ACCESS_TOKEN;
	}

}
