package com.cuzfou.ddapi.auth2;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDingtalkToken implements DingtalkToken {

	private final String url;
	private final String appKey;
	private final String appSecret;

	public DefaultDingtalkToken(String url, String appKey, String appSecret) {
		this.url = url;
		this.appKey = appKey;
		this.appSecret = appSecret;
	}

	@Override
	public String getToken() throws ApiException {
		Assert.isTrue(StringUtils.hasLength(appKey), "未配置dingtalk.corp.appKey");
		Assert.isTrue(StringUtils.hasLength(appSecret), "未配置dingtalk.corp.appSecret");
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setCorpid(appKey);
		request.setCorpsecret(appSecret);
		request.setHttpMethod("GET");
		OapiGettokenResponse response = client.execute(request);
		if (response.isSuccess()) {
			return response.getAccessToken();
		} else {
			log.info("获取AccessToken失败，响应的错误信息：{},请求的信息[url]={},[appkey]={},[appsecret]={}", response.getErrmsg(), url,
					appKey, appSecret);
			throw new RuntimeException(response.getErrmsg());
		}
	}

	@Override
	public void renewal() throws ApiException {
		getToken();
	}

}
