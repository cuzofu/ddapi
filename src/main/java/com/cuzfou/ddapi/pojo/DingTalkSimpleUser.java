package com.cuzfou.ddapi.pojo;

import lombok.Data;

@Data
public class DingTalkSimpleUser {
	
	private String name;
	private String userid;

	public DingTalkSimpleUser() {
	}

	public DingTalkSimpleUser(String name, String userid) {
		this.name = name;
		this.userid = userid;
	}
}