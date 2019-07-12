package com.cuzfou.ddapi.auth2;

import java.util.concurrent.TimeUnit;

import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessTokenRenewalTask implements Runnable {

	private DingtalkToken dingtalkToken;

	public AccessTokenRenewalTask(DingtalkToken dingtalkToken) {
		log.info("AccessToken续期任务初始化，获取access_token的Bean对象信息：{}", dingtalkToken.getClass().getName());
		this.dingtalkToken = dingtalkToken;
	}

	@Override
	public void run() {
		log.debug("执行AccessToken续期逻辑.");
		boolean run = true;
		while (run) {
			try {
				dingtalkToken.renewal();
				run = false;
				log.debug("AccessToken续期完成.");
			} catch (ApiException e) {
				log.error("执行AccessToken续期逻辑失败，错误信息：{}", e.getMessage());
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e1) {
				}
			}
		}
	}
}