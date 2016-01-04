package com.mre.action;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.Patient;
import com.mre.domain.Role;
import com.mre.domain.User;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2180567145200357041L;
	private Patient patient;
	/**
	 * 注册页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String registerUI() throws Exception {
		return "registerUI";
	}

	/**
	 * 注册，跳转到主页面 <只有患者需要注册，医生则由管理员直接给账号，初始化密码 123456 可以自行修改>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String register() throws Exception {
		Role role = roleService.getByName("患者");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		// 在页面中使用js进行了校验，所以这里要做的事情就是先验证用户名是否重复？？
		// 注意这里是要查整张表
		// 如果用户名重复，则跳回注册页面，并且告诉用户 errorMsg="用户名已存在"
		if (userService.findByLoginName(patient.getLoginName())) {
			addFieldError("errorMsg", "很抱歉，用户名已存在!");
			return "registerUI"; // 注意这里直接是"registerUI"
									// 不要使用toRegisterUI，因为这是一次请求
		} else {
			/* 保存User 对象 */
			// 使用Md5摘要，进行加密
			String md5Diagest = DigestUtils.md5Hex(patient.getPassword());
			patient.setPassword(md5Diagest);
			patient.setTrueName(patient.getLoginName()); // 默认真实姓名
			// 设置登录时间
			patient.setRegisterTime(new Date()); // 注册时间
			patient.setLastLoginTime(new Date());
			// 设置头像的存储位置, style/images/userIcons/<loginName.png> 存入数据库
			// model.setIcon(model.getLoginName()+".png");
			patient.setIcon("default.png"); // 默认给个头像
			patient.setIpAddress(ServletActionContext.getRequest()
					.getRemoteAddr()); // ip地址
			patient.setRoles(roles); // 设置身份为患者
			patientService.save(patient);

			/* 保存saveBean */
			saveBean.setDiscriminator("patient");
			saveBean.setLoginTimestamp(System.currentTimeMillis());

			// 注册成功，将病人放入session中
			ActionContext.getContext().getSession().put("user", patient);
			ActionContext.getContext().getSession().put("saveBean", saveBean);
			// 将用户放入Application
			// ActionContext.getContext().getApplication().put(patient.getLoginName(),
			// patient);

			// 将用户的sessionId放入Application,主要是用于session销毁时是否需要移除user
			String sessionId = ServletActionContext.getRequest().getSession()
					.getId();
			ActionContext.getContext().getApplication()
					.put(patient.getLoginName(), sessionId);
		}
		return "toIndex";
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginUI() throws Exception {

		return "loginUI";
	}

	/**
	 * 登录，跳转到主页面 登录进来要判断是医生 病人 还是治疗师
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		// -------------------------使用SQL语句查询 --------------------------
		/*
		 * if (!(userService.findByLoginNameAndPassword(model.getLoginName(),
		 * DigestUtils.md5Hex(model.getPassword()), saveBean))) {
		 * addFieldError("errorMsg", "用户名或密码错误！"); return "loginUI"; }
		 */
		// -------------------------使用HQL多态查询 ---------------------------
		String msg = userService.validateUser(model.getLoginName(),
				DigestUtils.md5Hex(model.getPassword()), saveBean);
		if (!(msg.equals("welcome"))) {
			addFieldError("errorMsg", msg);
			return "loginUI";
		}
		if (getSaveBean().getDiscriminator().equals("doctor")) {
			return "toDoctorCenter";
		} else if (getSaveBean().getDiscriminator().equals("therapist")) {
			return "toTherapistCenter";
		}
		return "toIndex";
	}

	/**
	 * 注销，跳转到主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		ServletActionContext.getRequest().getSession().invalidate();
		return "toIndex";
	}

	/**
	 * 跳转到用户中心，对用户做一下判断，选择是跳入病人中心还是医生中心
	 * 
	 * @return
	 * @throws Exception
	 */
	public String center() throws Exception {
		saveBean = getSaveBean();
		if (saveBean.getDiscriminator().equals("patient")) {
			return "toPatientCenter";

		} else if (saveBean.getDiscriminator().equals("user")) {
			return "toAdminCenter";

		} else if (saveBean.getDiscriminator().equals("doctor")) {
			return "toDoctorCenter";

		} else if (saveBean.getDiscriminator().equals("therapist")) {
			return "toTherapistCenter";
		}
		return null;
	}

	/**
	 * 跳转到用户的收件箱
	 * 
	 * @return
	 * @throws Exception
	 */
	public String message() throws Exception {
		saveBean = getSaveBean();
		if (saveBean.getDiscriminator().equals("patient")) {
			return "toPatient_receiveBoxList";

		} else if (saveBean.getDiscriminator().equals("doctor")) {
			return "toDoctor_receiveBoxList";

		} else if (saveBean.getDiscriminator().equals("therapist")) {
			return "toTherapist_receiveBoxList";
		}
		return null;
	}

	/**
	 * 加一个跳转到主页的方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		getMessagesCount();
		return "index";
	}

	/** ----------------Getter And Setter---------------- **/
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
