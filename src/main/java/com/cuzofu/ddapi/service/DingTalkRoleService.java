package com.cuzofu.ddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuzofu.ddapi.auth2.DingtalkOAuth2AccessToken;
import com.cuzofu.ddapi.properties.DingtalkProperties;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRoleAddroleRequest;
import com.dingtalk.api.request.OapiRoleAddrolegroupRequest;
import com.dingtalk.api.request.OapiRoleAddrolesforempsRequest;
import com.dingtalk.api.request.OapiRoleDeleteroleRequest;
import com.dingtalk.api.request.OapiRoleGetroleRequest;
import com.dingtalk.api.request.OapiRoleGetrolegroupRequest;
import com.dingtalk.api.request.OapiRoleListRequest;
import com.dingtalk.api.request.OapiRoleRemoverolesforempsRequest;
import com.dingtalk.api.request.OapiRoleSimplelistRequest;
import com.dingtalk.api.request.OapiRoleUpdateroleRequest;
import com.dingtalk.api.response.OapiRoleAddroleResponse;
import com.dingtalk.api.response.OapiRoleAddrolegroupResponse;
import com.dingtalk.api.response.OapiRoleAddrolesforempsResponse;
import com.dingtalk.api.response.OapiRoleDeleteroleResponse;
import com.dingtalk.api.response.OapiRoleGetroleResponse;
import com.dingtalk.api.response.OapiRoleGetrolegroupResponse;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleRemoverolesforempsResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.dingtalk.api.response.OapiRoleUpdateroleResponse;
import com.taobao.api.ApiException;

/**
 * 角色管理服务
 * 
 * @author cuzofu
 * @since 2019-07-12
 * @version 1.0
 */
@Component
@RestController
@RequestMapping("/role")
public class DingTalkRoleService {

	@Autowired
	private DingtalkOAuth2AccessToken dingtalkOAuth2AccessToken;
	@Autowired
	private DingtalkProperties dingtalkProperties;

	/**
	 * 获取角色列表
	 * 
	 * @param pageSize
	 *            分页大小，默认值：20，最大值200
	 * @param current
	 *            分页偏移，默认值：0
	 * 
	 * @return 
			{
			    "errcode":0,
			    "errmsg":"ok",
			    "result":{
			        "hasMore":false,
			        "list":[
			            {
		                    "name":"默认",
		                    "groupId":1,
		                    "roles":[
		                        {
	                                "name":"管理员",
	                                "id":1
		                        }
		                    ]
			            }
			        ]
			    }
			}
	 * @throws ApiException
	 */
	@GetMapping("/list")
	public OapiRoleListResponse getRoleList(Long pageSize, Long current) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleList());
		OapiRoleListRequest request = new OapiRoleListRequest();
		request.setHttpMethod("POST");
		request.setOffset(current);
		request.setSize(pageSize);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取角色下的员工列表
	 * 
	 * @param roleId
	 *            角色ID {@value Long}
	 * @param pageSize
	 *            分页大小，默认值：20，最大值200
	 * @param current
	 *            分页偏移，默认值：0
	 * @return 
		{
		    "errcode":0,
		    "errmsg":"ok",
		    "result":{
		        "hasMore":false,
		        "nextCursor":100,
		        "list":[
		            {
	                    "userid":"manager7978",
	                    "name":"小钉"
		            }
		        ]
		    }
		}
	 * @throws ApiException
	 */
	@GetMapping("/userSimpleList")
	public OapiRoleSimplelistResponse getUserSimpleListByRoleId(Long roleId, Long pageSize, Long current)
			throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleUserSimpleList());
		OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
		request.setHttpMethod("POST");
		request.setRoleId(roleId);
		request.setOffset(current);
		request.setSize(pageSize);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取角色组
	 * 
	 * @param groupId 角色组的Id
	 * @return
		{
		    "errcode": 0,
		    "errmsg": "ok",
		    "role_group": {
		        "roles":[
		            {
	                    "role_id":1,
	                    "role_name":"出纳"
		            }
		        ],
		        "group_name":"财务"
		    },
		}
	 * @throws ApiException
	 */
	@GetMapping("/group")
	public OapiRoleGetrolegroupResponse getRoleGroup(Long groupId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleGroup());
		OapiRoleGetrolegroupRequest request = new OapiRoleGetrolegroupRequest();
		request.setHttpMethod("POST");
		request.setGroupId(groupId);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取角色详情
	 * 
	 * @param roleId 角色Id
	 * @return
			{
			    "role":{
			        "name":"财务",
			        "groupId":1002
			    },
			    "errcode":0,
			    "errmsg":"成功"
			}
	 * @throws ApiException
	 */
	@GetMapping
	public OapiRoleGetroleResponse getRoleInfo(Long roleId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleInfo());
		OapiRoleGetroleRequest request = new OapiRoleGetroleRequest();
		request.setHttpMethod("POST");
		request.setRoleId(roleId);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 
	 * @param roleName
	 * 			角色名称
	 * @param groupId
	 * 			角色组id
	 * @return
	 * 		{
	 *     		"roleId":1,
	 *     		"errcode": 0,
	 *     		"errmsg": "ok"
	 * 		}
	 * @throws ApiException
	 */
	@PostMapping
	public OapiRoleAddroleResponse create(String roleName, Long groupId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleCreate());
		OapiRoleAddroleRequest request = new OapiRoleAddroleRequest();
		request.setHttpMethod("POST");
		request.setRoleName(roleName);
		request.setGroupId(groupId);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 更新角色
	 * 
	 * @param roleName 角色名称
	 * @param roleId 角色id
	 * @return
	 * 		{
	 *    		"errcode": 0,
	 *    		"errmsg": "ok"
	 *		}
	 * @throws ApiException
	 */
	@PutMapping
	public OapiRoleUpdateroleResponse update(String roleName, Long roleId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleUpdate());
		OapiRoleUpdateroleRequest request = new OapiRoleUpdateroleRequest();
		request.setHttpMethod("POST");
		request.setRoleName(roleName);
		request.setRoleId(roleId);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 删除角色
	 * 		【注意】删除角色前，需确保角色下面的员工没有被赋予这个角色
	 * @param roleId 角色id
	 * @return
		{
		    "errcode": 0,
		    "errmsg": "ok"
		}
	 * @throws ApiException
	 */
	@DeleteMapping
	public OapiRoleDeleteroleResponse delete(Long roleId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleDelete());
		OapiRoleDeleteroleRequest request = new OapiRoleDeleteroleRequest();
		request.setHttpMethod("POST");
		request.setRoleId(roleId);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 创建角色组
	 * 
	 * @param name 角色组名称
	 * @return
		{
		    "groupId":11,
		    "errcode": 0,
		    "errmsg": "ok"
		}
	 * @throws ApiException
	 */
	@PostMapping("/groupcreate")
	public OapiRoleAddrolegroupResponse createGroup(String name) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRoleGroupCreate());
		OapiRoleAddrolegroupRequest request = new OapiRoleAddrolegroupRequest();
		request.setHttpMethod("POST");
		request.setName(name);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 批量增加员工角色
	 * 
	 * @param roleIds 角色id list，最大列表长度：20 (示例：1,2,3,4,5)
	 * @param userIds 员工id list，最大列表长度：100 (示例：a,b,c,d,e)
	 * @return
			{
			    "errcode": 0,
			    "errmsg": "ok"
			}
	 * @throws ApiException
	 */
	@PostMapping("/forEmps")
	public OapiRoleAddrolesforempsResponse addRolesForEmps(String roleIds, String userIds) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRolesAddForEmps());
		OapiRoleAddrolesforempsRequest request = new OapiRoleAddrolesforempsRequest();
		request.setHttpMethod("POST");
		request.setRoleIds(roleIds);
		request.setUserIds(userIds);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 批量删除员工角色
	 * 
	 * @param roleIds 角色id list，最大列表长度：20 (示例：1,2,3,4,5)
	 * @param userIds 员工id list，最大列表长度：100 (示例：a,b,c,d,e)
	 * @return
		{
		    "errcode": 0,
		    "errmsg": "ok"
		}
	 * @throws ApiException
	 */
	@DeleteMapping("/forEmps")
	public OapiRoleRemoverolesforempsResponse deleteRolesForEmps(String roleIds, String userIds) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getRolesDeleteForEmps());
		OapiRoleRemoverolesforempsRequest request = new OapiRoleRemoverolesforempsRequest();
		request.setHttpMethod("POST");
		request.setRoleIds(roleIds);
		request.setUserIds(userIds);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

}
