package com.cuzofu.ddapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuzofu.ddapi.auth2.DingtalkOAuth2AccessToken;
import com.cuzofu.ddapi.pojo.DingTalkDepartment;
import com.cuzofu.ddapi.properties.DingtalkProperties;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentDeleteRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListIdsRequest;
import com.dingtalk.api.request.OapiDepartmentListParentDeptsByDeptRequest;
import com.dingtalk.api.request.OapiDepartmentListParentDeptsRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListIdsResponse;
import com.dingtalk.api.response.OapiDepartmentListParentDeptsByDeptResponse;
import com.dingtalk.api.response.OapiDepartmentListParentDeptsResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.taobao.api.ApiException;

/**
 * 钉钉部门管理服务
 * 
 * @author cuzofu
 */
@Component
@RestController
@RequestMapping("/cdd/department")
public class DingTalkDepartmentService {

	@Autowired
	private DingtalkOAuth2AccessToken dingtalkOAuth2AccessToken;
	@Autowired
	private DingtalkProperties dingtalkProperties;

	/**
	 * 创建部门
	 * 
	 * @param department 部门信息
			参数名					参数类型		必须		说明
			name				String		是		部门名称，长度限制为1~64个字符，不允许包含字符‘-’‘，’以及‘,’
			parentid			String		是		父部门id，根部门id为1
			order				String		否		在父部门中的排序值，order值小的排序靠前
			createDeptGroup		Boolean		否		是否创建一个关联此部门的企业群，默认为false
			deptHiding			Boolean		否		是否隐藏部门,
													true表示隐藏
													false表示显示
			deptPermits			String		否		可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的字符串，使用“|”符号进行分割。总数不能超过200
			userPermits			String		否		可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用“|”符号进行分割。总数不能超过200
			outerDept			Boolean		否		限制本部门成员查看通讯录，限制开启后，本部门成员只能看到限定范围内的通讯录。true表示限制开启
			outerPermitDepts	String		否		outerDept为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用“|”符号进行分割。总数不能超过200
			outerPermitUsers	String		否		outerDept为true时，可以配置额外可见人员，值为userid组成的的字符串，使用“|”符号进行分割。总数不能超过200
			outerDeptOnlySelf	Boolean		否		outerDept为true时，可以配置该字段，为true时，表示只能看到所在部门及下级部门通讯录
			sourceIdentifier	String		否		部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射
	 * @return {
	 *				"errcode":返回码,
	 *				"errmsg": "对返回码的文本描述内容",
	 *				"id": 创建的部门id
	 *		}
	 * @throws ApiException
	 */
	@PostMapping()
	public OapiDepartmentCreateResponse createDepartment(@RequestBody DingTalkDepartment department)
			throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentCreate());
		OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
		BeanUtils.copyProperties(department, request);
		request.setHttpMethod("POST");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 更新部门
	 * 
	 * @param department 部门信息
			参数名				参数类型		必须		说明
			id					String		是		部门id
			name				String		否		部门名称，长度限制为1~64个字符，不允许包含字符‘-’‘，’以及‘,’
			parentid			String		否		父部门id，根部门id为1
			order				String		否		在父部门中的排序值，order值小的排序靠前
			createDeptGroup		Boolean		否		是否创建一个关联此部门的企业群
			groupContainSubDept	Boolean		否		部门群是否包含子部门
			groupContainOuterDept	Boolean	否		部门群是否包含外包部门
			groupContainHiddenDept	Boolean	否		部门群是否包含隐藏部门
			autoAddUser			Boolean		否		如果有新人加入部门是否会自动加入部门群
			deptManagerUseridList	String	否		部门的主管列表,取值为由主管的userid组成的字符串，不同的userid使用“|”符号进行分割
			deptHiding			Boolean		否		是否隐藏部门,
													true表示隐藏
													false表示显示
			deptPermits			String		否		可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用“|”符号进行分割。总数不能超过200
			userPermits			String		否		可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的字符串，使用“|”符号进行分割。总数不能超过200
			outerDept			Boolean		否		是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己
			outerPermitDepts	String		否		本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割。总数不能超过200
			outerPermitUsers	String		否		本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用|符号进行分割。总数不能超过200
			outerDeptOnlySelf	Boolean		否		outerDept为true时，可以配置该字段，为true时，表示只能看到所在部门及下级部门通讯录
			orgDeptOwner		String		否		企业群群主
			sourceIdentifier	String		否		部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射
	 * @param lang 通讯录语言(默认zh_CN另外支持en_US)
	 * @return
	 * @throws ApiException
	 */
	@PutMapping()
	public OapiDepartmentUpdateResponse updateDepartment(@RequestBody DingTalkDepartment department,
			@RequestBody String lang) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentUpdate());
		OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
		BeanUtils.copyProperties(department, request);
		request.setLang(lang);
		request.setHttpMethod("POST");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 删除部门
	 * @param id 部门id (注：不能删除根部门；当部门里有员工，或者部门的子部门里有员工，也不能删除)
	 * @return
	 * 		参数			参数说明
	 * 		errcode		返回码
	 * 		errmsg		对返回码的文本描述内容
	 * @throws ApiException
	 */
	@DeleteMapping()
	public OapiDepartmentDeleteResponse deleteDepartment(String id) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentDelete());
		OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
		request.setId(id);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取子部门ID列表
	 * 
	 * @param id 部门id
	 * @return
	 * 		参数					参数说明
	 * 		errcode				返回码
	 * 		errmsg				对返回码的文本描述内容
	 * 		sub_dept_id_list	子部门ID列表数据
	 * @throws ApiException
	 */
	@GetMapping("/subDeptListIds")
	public OapiDepartmentListIdsResponse getSubDeptListIds(String id) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getSubDepartmentIdList());
		OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
		request.setId(id);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取部门列表
	 * 
	 * @param id
	 * 			部门id
	 * @param lang
	 * 			通讯录语言（默认zh_CN，未来会支持en_US）
	 * @param fetchChild
	 * 			是否递归部门的全部子部门，ISV微应用固定传递false
	 * @return
	 * 		参数				参数说明
	 * 		errcode			返回码
	 * 		errmsg			对返回码的文本描述内容
	 * 		department		部门列表数据，以部门的order字段从小到大排列
	 * 			id				部门id
	 *			name			部门名称
	 *			parentid		父部门id，根部门为1
	 *			createDeptGroup	是否同步创建一个关联此部门的企业群，true表示是，false表示不是
	 *			autoAddUser		当群已经创建后，是否有新人加入部门会自动加入该群, true表示是，false表示不是
	 * @throws ApiException
	 */
	@GetMapping()
	public OapiDepartmentListResponse getDeptList(String id, String lang, Boolean fetchChild) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentList());
		OapiDepartmentListRequest request = new OapiDepartmentListRequest();
		request.setId(id);
		request.setLang(lang);
		request.setFetchChild(fetchChild);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 获取部门详情
	 * 
	 * @param id
	 * 			部门id
	 * @param lang
	 * 			通讯录语言（默认zh_CN，未来会支持en_US）
	 * @return
	 * 			参数						说明
	 * 			id						部门id
	 * 			name					部门名称
	 * 			parentid				父部门id，根部门为1
	 * 			order					当前部门在父部门下的所有子部门中的排序值
	 * 			createDeptGroup			是否同步创建一个关联此部门的企业群，true表示是，false表示不是
	 * 			autoAddUser				当部门群已经创建后，是否有新人加入部门会自动加入该群，true表示是，false表示不是
	 * 			deptHiding				是否隐藏部门，true表示隐藏，false表示显示
	 * 			deptPermits				可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用“|”符号进行分割
	 * 			userPermits				可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用“|”符号进行分割
	 * 			outerDept				是否本部门的员工仅可见员工自己，为true时，本部门员工默认只能看到员工自己
	 * 			outerPermitDepts		本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用“|”符号进行分割
	 * 			outerPermitUsers		本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用“|”符号进行分割
	 * 			orgDeptOwner			企业群群主
	 * 			deptManagerUseridList	部门的主管列表，取值为由主管的userid组成的字符串，不同的userid使用“|”符号进行分割
	 * 			sourceIdentifier		部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射
	 * 			groupContainSubDept		部门群是否包含子部门
	 * @throws ApiException
	 */
	@GetMapping("/deptInfo")
	public OapiDepartmentGetResponse getDeptInfo(String id, String lang) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentInfo());
		OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
		request.setId(id);
		request.setLang(lang);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 查询部门的所有上级父部门路径
	 * 
	 * @param id 希望查询的部门的id，包含查询的部门本身
	 * @return parentIds	该部门的所有父部门id列表([789,456,123,1])
	 * @throws ApiException
	 */
	@GetMapping("/parents/id")
	public OapiDepartmentListParentDeptsByDeptResponse getDepartmentParentByDeptId(String id) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentParentByDeptId());
		OapiDepartmentListParentDeptsByDeptRequest request = new OapiDepartmentListParentDeptsByDeptRequest();
		request.setId(id);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

	/**
	 * 
	 * @param userid 希望查询的用户的id
	 * @return parentIds	该部门的所有父部门id列表([[456,123,1],[789,1]])
	 * @throws ApiException
	 */
	@GetMapping("/parents/userid")
	public OapiDepartmentListParentDeptsResponse getDepartmentParentByUserid(String userid) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(dingtalkProperties.getApi().getDepartmentParentByUserid());
		OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
		request.setUserId(userid);
		request.setHttpMethod("GET");
		return client.execute(request, dingtalkOAuth2AccessToken.getAccessToken());
	}

}
