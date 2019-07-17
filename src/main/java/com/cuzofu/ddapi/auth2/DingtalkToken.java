package com.cuzofu.ddapi.auth2;

import com.taobao.api.ApiException;

/**
 * 获取钉钉AccessToken
 * 
 * @author liang
 *
 */
public interface DingtalkToken {

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	String getToken() throws ApiException;

	/**
	 * 续期accesss_token
	 */
	void renewal() throws ApiException;

}
