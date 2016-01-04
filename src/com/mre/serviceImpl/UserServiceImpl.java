package com.mre.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Doctor;
import com.mre.domain.Patient;
import com.mre.domain.Therapist;
import com.mre.domain.User;
import com.mre.service.UserService;
import com.mre.util.Formatter;
import com.mre.util.domain.SaveBean;
import com.opensymphony.xwork2.ActionContext;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements
		UserService {

	@Override
	public boolean findByLoginName(String loginName) {

		/*
		 * Object o = (Object)
		 * sessionFactory.getCurrentSession().createSQLQuery( //
		 * "SELECT * FROM _User WHERE loginName='" + loginName + "'" //
		 * ).uniqueResult();
		 */
		Object o = (Object) getSession().createQuery( //
				"FROM User u WHERE u.loginName='" + loginName + "'")//
				.uniqueResult();
		if (o != null) {
			return true;
		}
		return false;
	}

	@Deprecated
	@Override
	public boolean findByLoginNameAndPassword(String loginName,
			String password, SaveBean saveBean) {
		// 获取SQLQuery 对象，这里存的是“裸”对象
		SQLQuery sqlQuery = getSession().createSQLQuery(
				"SELECT * FROM _user WHERE loginName='" + loginName
						+ "' AND password='" + password + "'");
		if (sqlQuery != null) {
			User user = null;
			Object object = sqlQuery.uniqueResult();
			// 得到discriminator的值，并做判断
			String discriminator = ((Object[]) object)[0] + "";
			System.out.println("discriminator=" + discriminator);
			if (discriminator.equals("user")) {
				user = new User();
				user = (User) sqlQuery.addEntity(User.class).uniqueResult();
			} else if (discriminator.equals("patient")) {
				user = new Patient();
				user = (Patient) sqlQuery.addEntity(Patient.class)
						.uniqueResult();
			} else if (discriminator.equals("doctor")) {
				user = new Doctor();
				user = (Doctor) sqlQuery.addEntity(Doctor.class).uniqueResult();
			} else if (discriminator.equals("therapist")) {
				user = new Therapist();
				user = (Therapist) sqlQuery.addEntity(Therapist.class)
						.uniqueResult();
			}
			// 保存区分字段
			saveBean.setDiscriminator(discriminator);
			// 保存ipAddr
			user.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
			user.setLastLoginTime(new Date());
			this.update(user);
			saveBean.setLoginTimestamp(System.currentTimeMillis());
			ActionContext.getContext().getSession().put("saveBean", saveBean);
			ActionContext.getContext().getSession().put("user", user);
			return true;
		}
		return false;
	}

	/**
	 * 思路： 1. 登录时，根据用户名和密码查询用户，用户不存在，则返回"用户名或密码错误!"，存在则进行步骤2 2. 遍历Application中的
	 * userSessionMap， 判断是否存在user, 如不存在，则返回"welcome"，存在则进行步骤3 3.
	 * 取出userSessionMap中的user，
	 * 判断user.getIpAddress()是否与当下相同，如相同，error="login"，放行.如果不相同，则进行步骤4 4.
	 * 返回"您的用户名已在其他地区登录！" 防止sql注入： 使用SELECT u.password FROM User u WHERE
	 * u.loginName = loginName 或者使用预处理命令。
	 */
	@Override
	public String validateUser(String loginName, String password,
			SaveBean saveBean) {
		String errorMsg = "";
		/*
		 * Object object = getSession() .createQuery( //
		 * "FROM User u WHERE u.loginName='" + loginName + "' AND u.password='"
		 * + password + "'")// .uniqueResult();
		 */
		// 防止sql注入，使用预处理命令
		Object object = getSession()
				.createQuery(
						"FROM User u WHERE u.loginName=:loginName AND u.password=:password")
				.setParameter("loginName", loginName)
				.setParameter("password", password).uniqueResult();
		if (object == null) {
			errorMsg = "用户名或密码错误!";
			return errorMsg;
		} else {
			// 处理单用户登录的问题
			User tempUser = (User) object;
			Map<String, Object> usersMap = ActionContext.getContext()
					.getApplication();
			// 是否找到user
			if (usersMap.containsKey(tempUser.getLoginName())) {
				// 这里要判断 ipAddr是否相同
				if (!tempUser.getIpAddress().equals(
						ServletActionContext.getRequest().getRemoteAddr())) {
					errorMsg = "您的账户已经在其他地区登录！";
					return errorMsg;
				} else {
					// 同一IP下登陆
					// 移除上一个session的user
					ActionContext.getContext().getApplication()
							.remove(tempUser.getLoginName());
					errorMsg = "login";
				}
			}
			User user = null;
			String discriminator = "";
			if (object instanceof Patient) {
				user = (Patient) object;
				discriminator = "patient";
			} else if (object instanceof Doctor) {
				user = (Doctor) object;
				discriminator = "doctor";
			} else if (object instanceof Therapist) {
				user = (Therapist) object;
				discriminator = "therapist";
			} else {
				user = (User) object;
				discriminator = "user";
			}
			// 保存ipAddr
			user.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
			// 保存此次登陆时间
			user.setLastLoginTime(new Date());
			this.update(user);

			System.out.println("discriminator=" + discriminator);
			// 保存区分字段
			saveBean.setDiscriminator(discriminator);
			saveBean.setLoginTimestamp(System.currentTimeMillis());
			ActionContext.getContext().getSession().put("saveBean", saveBean);
			ActionContext.getContext().getSession().put("user", user);

			// 向application中存储新的sessionId和user;
			if (!errorMsg.equals("login")) {
				System.out.println("用户普通登录...");
			} else {
				System.out.println("用户异常恢复后的登录...");
			}
			// 放入新的sessionId
			ActionContext
					.getContext()
					.getApplication()
					.put(user.getLoginName(),
							ServletActionContext.getRequest().getSession()
									.getId());
			return "welcome";
		}
	}

	@Override
	public void quitAndUpdateUser(SaveBean saveBean, Object object) {
		if ((null != saveBean) && (null != object)) {
			User user = null;
			if (object instanceof Patient) {
				user = (Patient) object;
			} else if (object instanceof Doctor) {
				user = (Doctor) object;
			} else if (object instanceof Therapist) {
				user = (Therapist) object;
			} else {
				user = (User) object;
			}
			// 累计上线时间，单位为小时
			int onlineTime = user.getOnlineTime() //
					+ (int) ((System.currentTimeMillis() - saveBean
							.getLoginTimestamp()) / (3600 * 1000));
			// 等级 20小时为一级
			int level = user.getLevel() + (onlineTime / 20);
			user.setOnlineTime(onlineTime);
			user.setLevel(level);
			// 更新用户，注意这里使用的是父类 UserService
			this.update(user);
		}
	}

	@Override
	public User getByLoginName(String loginName) {
		return (User) getSession().createQuery( //
				"FROM User u WHERE u.loginName='" + loginName + "'")//
				.uniqueResult();
	}

	@Override
	public Map<String, Object> bindAccountAndGetIdentity(String username,
			String password, String openid) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查询openid是否已经被绑定了
		User user = (User) getSession().createQuery( //
				"FROM User u WHERE u.openId='" + openid + "'")//
				.uniqueResult();
		if(user != null){
			// 如果已经被绑定了，而且，不是绑定在用户的MRE账号，那么就给出以下提示！
			if(!user.getLoginName().equals(username)){
				result.put("errorMsg", "抱歉，您的微信号已经绑定在其他MRE平台的账号上了！");
				return result;
			}	 
		}
		result.put("openid", openid);
		Object o = getSession()
				.createQuery(
						"FROM User u WHERE u.loginName=:loginName AND u.password=:password")
				.setParameter("loginName", username)
				.setParameter("password", password).uniqueResult();
		if (o != null) {
			String identity = "";
			if (o instanceof Patient)
				identity = "patient";
			else if (o instanceof Doctor)
				identity = "doctor";
			else if (o instanceof Therapist)
				identity = "therapist";
			else
				identity = "admin";
			// 保存openid
			User u = (User) o;
			u.setOpenId(openid);
			this.update(u);
			result.put("identity_name", identity);
			result.put("bind_result", true);
		} else {
			result.put("bind_result", false);
		}
		return result;
	}

	@Override
	public Map<String, Object> getByOpenid(String openid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Object o = getSession().createQuery( //
				"FROM User u WHERE u.openId='" + openid + "'")//
				.uniqueResult();
		if (o != null) {
			User u = null;
			String identity = "";
			if (o instanceof Doctor) {
				u = (Doctor) o;
				identity = "医生";
			} else if (o instanceof Therapist) {
				u = (Therapist) o;
				identity = "治疗师";
			} else if (o instanceof Patient) {
				u = (Patient) o;
				identity = "患者";
			}
			result.put("身份", identity);
			result.put("用户名", u.getLoginName());
			result.put("真实姓名", u.getTrueName());
			result.put("性别", u.getGender());
			result.put("出生年月",
					Formatter.formatWithPattern(u.getBirthday(), "yyyy-MM-dd"));
			result.put("所在城市", u.getCity());
			result.put("邮箱", u.getEmail());
			result.put("手机", u.getTelephone());
			result.put("微信", u.getWeixin());
			result.put("所在医院", u.getHospital().getName());
			result.put("注册时间", Formatter.formatWithPattern(u.getRegisterTime(),
					"yyyy-MM-dd HH:mm:ss"));
			result.put("上次登录时间", Formatter.formatWithPattern(
					u.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
		} else {
			result.put("errorMsg", "抱歉，您还没有进行绑定操作！");
		}
		return result;
	}
}
