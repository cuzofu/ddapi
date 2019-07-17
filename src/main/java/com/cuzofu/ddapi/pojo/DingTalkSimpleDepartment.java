package com.cuzofu.ddapi.pojo;

import lombok.Data;

/**
 * 钉钉部门简单信息
 * 
 * @author cuzofu
 *
 */
@Data
public class DingTalkSimpleDepartment {

	/** 部门id */
	private String id;

	/** 部门名称 */
	private String name;

	/** 父部门id，根部门为1 */
	private String parentid;

	/** 是否同步创建一个关联此部门的企业群，true表示是，false表示不是 */
	private Boolean createDeptGroup;

	/** 当部门群已经创建后，是否有新人加入部门会自动加入该群，true表示是，false表示不是 */
	private Boolean autoAddUser;

}
