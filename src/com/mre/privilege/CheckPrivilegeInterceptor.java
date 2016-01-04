package com.mre.privilege;

import java.util.ArrayList;
import java.util.List;

import com.mre.domain.Doctor;
import com.mre.domain.Patient;
import com.mre.domain.Therapist;
import com.mre.domain.User;
import com.mre.util.domain.SaveBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2148693079198738459L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("进入拦截器...");
		// 拦截返回值
		String result = "";

		/** 得到url,检验是否合法 **/
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String priUrl = "";
		// namespace == /json时, url要加上 namespace + "/" + actionName
 		if (namespace.equals("/dev"))
			priUrl = namespace + "/" + actionName;
		else
			priUrl = namespace + actionName; 

		System.out.println("priUrl=" + priUrl);

		/** 未登录即可访问的普通权限列表 **/
		List<String> commonLogoutUrls = new ArrayList<String>();
		commonLogoutUrls.add("/user_index"); // 主页
		commonLogoutUrls.add("/user_registerUI"); // 登录注册
		commonLogoutUrls.add("/user_register");
		commonLogoutUrls.add("/user_loginUI");
		commonLogoutUrls.add("/user_login");
		commonLogoutUrls.add("/equipment_"); // 查看康复设备
		commonLogoutUrls.add("/hospital_"); // 查看合作医院
		commonLogoutUrls.add("/expert_"); // 查看康复专家
		commonLogoutUrls.add("/assessment_"); // 查看康复量表
		commonLogoutUrls.add("/forum_"); // 查看论坛
		commonLogoutUrls.add("/topic_list"); // 康复论坛的话题
		commonLogoutUrls.add("/topic_show"); // 查看话题

		/** 登录之后的普通权限 **/
		List<String> commonLoginUrls = new ArrayList<String>();
		commonLoginUrls.add("/user_index"); // 主页
		commonLoginUrls.add("/user_logout"); // 登出
		commonLoginUrls.add("/user_center"); // 中心
		commonLoginUrls.add("/user_message"); // 察看用户未读消息
		commonLoginUrls.add("/equipment_"); // 查看康复设备
		commonLoginUrls.add("/hospital_"); // 查看合作医院
		commonLoginUrls.add("/expert_"); // 查看康复专家
		commonLoginUrls.add("/assessment_"); // 查看康复量表
		commonLoginUrls.add("/forum_"); // 查看论坛
		commonLoginUrls.add("/topic_"); // 康复论坛的话题
		commonLoginUrls.add("/reply_"); // 回复

		// 得到saveBean对象，判断是哪个用户，然后检验权限
		SaveBean saveBean = (SaveBean) ActionContext.getContext().getSession()
				.get("saveBean");

		// 用户
		User user = (User) ActionContext.getContext().getSession().get("user");

		// 得到pri的开始部分，以“_”结尾
		String priUrlStartWith = priUrl.split("_")[0] + "_";
		// System.out.println("priUrlStartWith="+priUrlStartWith);

		// 未登录
		// 1. 就是没有登录
		// 2. 登录了没有退出来，但是session还没有过期，而在同一IP地址下的Application中已经没有用户了，这时候也要重新登录
		if ((user == null)
				|| (!ActionContext.getContext().getApplication()
						.containsKey(user.getLoginName()))) {

			System.out.println("普通权限");
			// 这时候放行所有的普通浏览权限
			if (commonLogoutUrls.contains(priUrlStartWith)
					|| commonLogoutUrls.contains(priUrl)) {
				result = invocation.invoke();
				System.out.println("退出拦截器...");
				return result;
			} else {
				// 这时候转到登录页面
				result = "loginUI";
				System.out.println("退出拦截器...");
				return result;
			}

		} else {

			if (saveBean.getDiscriminator().equals("user")) {
			} else if (saveBean.getDiscriminator().equals("doctor")) {
				user = (Doctor) user;
			} else if (saveBean.getDiscriminator().equals("therapist")) {
				user = (Therapist) user;
			} else {
				user = (Patient) user;
			}
			// 如果已经登录，再次登录的话就会提示，您已经登录，请先退出账户！
			if (priUrl.startsWith("/user_register")
					|| priUrl.startsWith("/user_login")) {
				System.out.println("重复登录错误");
				result = "loginError";
				System.out.println("退出拦截器...");
				return result;
			}
			// 登录之后的基本权限,放行
			else if (commonLoginUrls.contains(priUrlStartWith)
					|| commonLoginUrls.contains(priUrl)) {
				System.out.println("用户基本权限");
				result = invocation.invoke();
				System.out.println("退出拦截器...");
				return result;
			} else if (user.checkPrivilegeByUrl(priUrl)) {
				System.out.println("用户特殊权限");
				result = invocation.invoke();
				System.out.println("退出拦截器...");
				return result;
			}
			return "noPrivilegeError";
		}
	}
}
