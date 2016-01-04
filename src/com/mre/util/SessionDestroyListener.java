package com.mre.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mre.domain.User;
import com.mre.service.UserService;
import com.mre.util.domain.SaveBean;

public class SessionDestroyListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println(">---session "+se.getSession().getId()+" 创建成功---<");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println(">---session "+se.getSession().getId()+" 准备销毁---<");
		
		HttpSession session = se.getSession();
		// 获取与容器相关的容器对象 WebApplicationContextUtils 这个要记住，很必要
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(session.getServletContext());
		
	    // 得到userService
		UserService userService = (UserService) ac.getBean("userServiceImpl");
		User user = (User)session.getAttribute("user");
		
		/** 检测当前要销毁的session是否和action中的session是同一个
		 * 1. 是同一个，则是正常状态下session过期，从application中移除
		 * 2. 不是同一个，则是异常情况下的session过期，不作移除工作
		 */
		if(session.getServletContext().getAttribute(user.getLoginName()).equals(session.getId())){
			System.out.println("移除的用户:"+user.getLoginName());
			session.getServletContext().removeAttribute(user.getLoginName());
		}
		userService.quitAndUpdateUser((SaveBean)session.getAttribute("saveBean"),
				session.getAttribute("user"));
		
		System.out.println(">---session "+se.getSession().getId()+" 已经销毁---<");
	}

}
