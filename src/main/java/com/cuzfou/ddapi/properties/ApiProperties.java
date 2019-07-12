package com.cuzfou.ddapi.properties;

import lombok.Data;

@Data
public class ApiProperties {

	private String oapi;

	/** 获取access_token */
	private String accessToken;

	/** 获取管理员列表 */
	private String userAdmin;

	/** 获取用户详情 */
	private String userInfo;

	/** 创建用户 */
	private String userCreate;

	/** 更新用户 */
	private String userUpdate;

	/** 删除用户 */
	private String userDelete;

	/** 批量删除用户 */
	private String userBatchDelete;

	/** 获取部门用户（简单信息）列表 */
	private String userSimpleList;

	/** 获取部门用户详情列表 */
	private String userList;

	/** 获取部门用户详情分页列表 */
	private String userPageList;

	/** 获取部门用户userid列表 */
	private String userDeptMember;

	/** 获取管理员通讯录权限范围 */
	private String userAdminScope;

	/** 根据unionid获取userid */
	private String useridByUnionid;

	/** 获取企业员工人数 */
	private String orgUserCount;

	/** 获取部门列表 */
	private String departmentList;

	/** 获取部门详情 */
	private String departmentInfo;

	/** 获取子部门ID列表 */
	private String subDepartmentIdList;

	/** 创建部门 */
	private String departmentCreate;

	/** 更新部门 */
	private String departmentUpdate;

	/** 删除部门 */
	private String departmentDelete;

	/** 查询部门的所有上级父部门路径 */
	private String departmentParentByDeptId;

	/** 查询指定用户的所有上级父部门路径 */
	private String departmentParentByUserid;

	/** 获取角色列表 */
	private String roleList;

	/** 获取角色下的员工列表 */
	private String roleUserSimpleList;

	/** 获取角色组 */
	private String roleGroup;

	/** 获取角色详情 */
	private String roleInfo;

	/** 创建角色 */
	private String roleCreate;

	/** 更新角色 */
	private String roleUpdate;

	/** 删除角色 */
	private String roleDelete;

	/** 创建角色组 */
	private String roleGroupCreate;

	/** 批量增加员工角色 */
	private String rolesAddForEmps;

	/** 批量删除员工角色 */
	private String rolesDeleteForEmps;

}
