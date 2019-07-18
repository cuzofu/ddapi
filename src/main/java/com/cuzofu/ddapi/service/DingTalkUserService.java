package com.cuzofu.ddapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cuzofu.ddapi.auth2.DingtalkOAuth2AccessToken;
import com.cuzofu.ddapi.pojo.DingTalkUser;
import com.cuzofu.ddapi.properties.DingtalkProperties;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserBatchdeleteRequest;
import com.dingtalk.api.request.OapiUserCreateRequest;
import com.dingtalk.api.request.OapiUserDeleteRequest;
import com.dingtalk.api.request.OapiUserGetOrgUserCountRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiUserBatchdeleteResponse;
import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserDeleteResponse;
import com.dingtalk.api.response.OapiUserGetOrgUserCountResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.taobao.api.ApiException;

@Component
@RestController
@RequestMapping("/cdd/user")
public class DingTalkUserService {

	@Autowired
	private DingtalkOAuth2AccessToken dingtalkOAuth2AccessToken;
	@Autowired
	private DingtalkProperties dingtalkProperties;

	/**
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return { "errcode": 0, "unionid": "PiiiPyQqBNBii0HnCJ3zljcuAiEiE",
	 *         //在当前isv全局范围内唯一标识一个用户的身份,用户无法修改 "openId":
	 *         "PiiiPyQqBNBii0HnCJ3zljcuAiEiE", //在本 服务窗运营服务商
	 *         范围内，唯一标识关注者身份的id（不可修改） "roles": [{ //角色信息（ISV不可见），json数组格式 "id":
	 *         23003585, //角色id（ISV不可见） "name": "engineer", //角色名称（ISV不可见）
	 *         "groupName": "group one" //角色分组名称（ISV不可见） }], "remark": "remark",
	 *         //备注（ISV不可见） "userid": "zhangsan", //员工唯一标识ID（不可修改）
	 *         "isLeaderInDepts": "{1:false}",
	 *         //在对应的部门中是否为主管：Map结构的json字符串，key是部门的Id，value是人员在这个部门中是否为主管，true：表示是
	 *         false：表示不是 "isBoss": false,
	 *         //是否为企业的老板，true表示是，false表示不是（【设置负责人】：主管理员登陆钉钉手机客户端
	 *         -【通讯录】-【企业名后面的管理】-【企业通讯录】-【负责人设置】进行添加即可。） "hiredDate":
	 *         1520265600000, //入职时间 "isSenior": false, //是否是高管 "tel":
	 *         "010-88996533", //分机号（仅限企业内部开发调用） "department": [1,2],
	 *         //成员所属部门id列表 "workPlace": "beijing", //办公地点（ISV不可见） "email":
	 *         "ceshi@aliyun.com", //员工的电子邮箱（ISV不可见） "orgEmail" :
	 *         "ceshi@aliyun.com" //员工的企业邮箱，如果员工已经开通了企业邮箱，接口会返回，否则不会返回（ISV不可见）
	 *         "orderInDepts": "{1:71738366882504}",
	 *         //在对应的部门中的排序，Map结构的json字符串，key是部门的Id，value是人员在这个部门的排序值 "mobile":
	 *         "15901516821", //手机号码（ISV不可见） "errmsg": "ok", "active": false,
	 *         //是否已经激活，true：表示已激活 false：表示未激活 "avatar": "dingtalk.com/abc.jpg",
	 *         //头像url "isAdmin": false, //是否为企业的管理员，true：表示是 false：表示不是
	 *         "isHide": false, //是否号码隐藏,true：表示隐藏,false：表示不隐藏 "jobnumber":
	 *         "001", //员工工号 "name": "test1", //成员名称 "extattr": {},
	 *         //扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置)
	 *         "stateCode": "86", //手机号码区号 "position": "manager" //职位信息 }
	 * @throws ApiException
	 */
	@GetMapping
	public OapiUserGetResponse getUser(String userId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserInfo());
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userId);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 创建用户
	 * 
	 * @param user
	 * @see DingTalkUser
	 * @link {com.xujicheng.dingtalk.pojo.DingTalkUser}
	 * @return { "errcode": 0, "errmsg": "created", "userid": "zhangsan"
	 *         //员工唯一标识 }
	 * @throws ApiException
	 */
	@PostMapping("/create")
	public OapiUserCreateResponse createUser(@RequestBody DingTalkUser user) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserCreate());
		OapiUserCreateRequest request = getOapiUserCreateRequest(user);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @see DingTalkUser
	 * @link {com.xujicheng.dingtalk.pojo.DingTalkUser}
	 * @return { "errcode": 0, "errmsg": "updated" }
	 * @throws ApiException
	 */
	@PostMapping("/update")
	public OapiUserUpdateResponse updateUser(@RequestBody DingTalkUser user) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserUpdate());
		OapiUserUpdateRequest request = getOapiUserUpdateRequest(user);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            员工唯一标识
	 * @return { "errcode": 0, "errmsg": "deleted" }
	 * @throws ApiException
	 */
	@DeleteMapping
	public OapiUserDeleteResponse deleteUser(String userId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserDelete());
		OapiUserDeleteRequest request = new OapiUserDeleteRequest();
		request.setUserid(userId);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 批量删除用户
	 * 
	 * @param users
	 *            员工UserID列表，列表长度在1~20之间
	 * @return { "errcode": 0, "errmsg": "deleted" }
	 * @throws ApiException
	 */
	@DeleteMapping("/batch")
	public OapiUserBatchdeleteResponse batchdeleteUser(List<String> users) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserBatchDelete());
		OapiUserBatchdeleteRequest request = new OapiUserBatchdeleteRequest();
		request.setUseridlist(users);
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 *
	 * @param departmentId
	 *            获取的部门id
	 * @param page
	 *            当前页 不分页传入null
	 * @param size
	 *            每页显示的条数 最大100
	 * @return { "errcode": 0, "errmsg": "ok", "hasMore": false,
	 *         //在分页查询时返回，代表是否还有下一页更多数据 "userlist": [ //成员列表 { "userid":
	 *         "zhangsan", //员工唯一标识ID（不可修改） "name": "张三" //成员名称 } ] }
	 * @throws ApiException
	 */
	@GetMapping("/simple")
	public OapiUserSimplelistResponse getUserSimpleList(Long departmentId, Long page, Long size) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserSimpleList());
		OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
		if (null != page) {
			if (null == size || size <= 0) {
				size = 10L;
			}
			request.setOffset(page);
			request.setSize(size);
		}
		request.setDepartmentId(departmentId);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());

	}

	/**
	 *
	 * @param departmentId
	 *            获取的部门id
	 * @param page
	 *            当前页 不分页传入null
	 * @param size
	 *            每页显示的条数 最大100
	 * @return { "errcode": 0, "errmsg": "ok", "hasMore": false,
	 *         //在分页查询时返回，代表是否还有下一页更多数据 "userlist":[ //成员列表 { "userid":
	 *         "zhangsan", //员工唯一标识ID（不可修改） "unionid":
	 *         "PiiiPyQqBNBii0HnCJ3zljcuAiEiE", //在当前isv全局范围内唯一标识一个用户的身份，用户无法修改
	 *         "mobile": "13122222222", //手机号（ISV不可见） "tel" : "010-123333",
	 *         //分机号（ISV不可见） "workPlace" :"", //办公地点（ISV不可见） "remark" : "",
	 *         //备注（ISV不可见） "order" : 1,
	 *         //表示人员在此部门中的排序，列表是按order的倒序排列输出的，即从大到小排列输出的（钉钉管理后台里面调整了顺序的话order才有值）
	 *         "isAdmin": true, //是否是企业的管理员,true表示是 false表示不是 "isBoss": false,
	 *         //是否为企业的老板（不能通过接口进行设置，可以通过钉钉管理后台进行设置）， true表示是 false表示不是
	 *         "isHide": true, //是否隐藏号码， true表示是 false表示不是 "isLeader": true,
	 *         //是否是部门的主管，true表示是，false表示不是 "name": "张三", //成员名称 "active": true,
	 *         //表示该用户是否激活了钉钉 "department": [1, 2], //成员所属部门id列表 "position":
	 *         "工程师", //职位信息 "email": "zhangsan@alibaba-inc.com", //员工的邮箱
	 *         "avatar": "./dingtalk/abc.jpg", //头像url "jobnumber": "111111",
	 *         //员工工号 "hiredDate": 1520265600000, //入职时间 "extattr": { //扩展属性
	 *         可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，
	 *         请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置) "爱好":"旅游", "年龄":"24"
	 *         } } ] }
	 * @throws ApiException
	 */
	@GetMapping("/list")
	public OapiUserListResponse getUserList(Long departmentId, Long page, Long size) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getUserList());
		OapiUserListRequest request = new OapiUserListRequest();
		request.setDepartmentId(departmentId);
		if (null != page) {
			if (null == size || size <= 0) {
				size = 10L;
			}
			request.setOffset(page);
			request.setSize(size);
		}
		request.setHttpMethod("GET");

		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());

	}

	/**
	 * 
	 * @param onlyActive
	 *            0：包含未激活钉钉的人员数量; 1：不包含未激活钉钉的人员数量
	 * @return { "count": 企业员工数量, "errcode": 返回码, "errmsg": 对返回码的文本描述内容 }
	 * @throws ApiException
	 */
	@GetMapping("/orgUserCount")
	public OapiUserGetOrgUserCountResponse getOrgUserCount(Long onlyActive) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getOrgUserCount());
		OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
		request.setOnlyActive(onlyActive);
		request.setHttpMethod("GET");
		OapiUserGetOrgUserCountResponse response = client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
		return response;
	}

	private OapiUserUpdateRequest getOapiUserUpdateRequest(DingTalkUser user) {
		OapiUserUpdateRequest request = new OapiUserUpdateRequest();
		request.setUserid(user.getUserid());
		request.setMobile(user.getMobile());
		request.setName(user.getName());
		// 需要用字符串， "[59869009,60345027]" 这种格式
		request.setDepartment(user.getDepartment());
		request.setEmail(user.getEmail());
		request.setWorkPlace(request.getWorkPlace());
		if (null == user.getExtattr()) {
			request.setExtattr(new JSONObject().toJSONString());
		} else {
			request.setExtattr(user.getExtattr().toJSONString());
		}

		request.setIsHide(user.getIsHide());
		request.setIsSenior(user.getIsSenior());
		request.setJobnumber(user.getJobnumber());
		if (null == user.getOrderInDepts()) {
			request.setOrderInDepts(new JSONObject().toJSONString());
		} else {
			request.setOrderInDepts(user.getOrderInDepts().toJSONString());

		}
		request.setOrgEmail(user.getOrgEmail());
		request.setPosition(user.getPosition());
		request.setRemark(user.getRemark());
		request.setTel(user.getTel());
		return request;
	}

	private OapiUserCreateRequest getOapiUserCreateRequest(DingTalkUser user) {
		OapiUserCreateRequest request = new OapiUserCreateRequest();
		request.setUserid(user.getUserid());
		request.setMobile(user.getMobile());
		request.setName(user.getName());
		// 需要用字符串， "[59869009,60345027]" 这种格式
		request.setDepartment(JSON.toJSONString(user.getDepartment()));
		request.setEmail(user.getEmail());
		request.setWorkPlace(request.getWorkPlace());
		if (null == user.getExtattr()) {
			request.setExtattr(new JSONObject().toJSONString());
		} else {
			request.setExtattr(user.getExtattr().toJSONString());
		}
		request.setIsHide(user.getIsHide());
		request.setIsSenior(user.getIsSenior());
		request.setJobnumber(user.getJobnumber());
		if (null == user.getOrderInDepts()) {
			request.setOrderInDepts(new JSONObject().toJSONString());
		} else {
			request.setOrderInDepts(user.getOrderInDepts().toJSONString());

		}
		request.setOrgEmail(user.getOrgEmail());
		request.setPosition(user.getPosition());
		request.setRemark(user.getRemark());
		request.setTel(user.getTel());
		return request;
	}
}
