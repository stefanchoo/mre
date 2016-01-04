package com.mre.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.Message;
import com.mre.domain.Patient;
import com.mre.domain.RehabExperience;
import com.mre.domain.RehabPlan;
import com.mre.domain.User;
import com.mre.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class PatientAction extends BaseAction<Patient> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1509986386086715852L;
	/**
	 * 拦截器的功能类似于web.xml文件中的Filter，能对用户的请求进行拦截，通过拦截用户的请求来实现对页面的控制。
	 * 拦截器是在Struts-core-2.2.3.jar中进行配置的，原始的拦截器是在struts-default.xml中配置的，里面封
	 * 存了拦截器的基本使用方法。Struts2拦截器功能类似于Servlet过滤器。在Action执行execute方法前，Struts2
	 * 会首先执行struts.xml中引用的拦截器，如果有多个拦截器则会按照上下顺序依次执行，在执行完所有的拦截器的
	 * interceptor方法后，会执行Action的execute方法。
	 **/

	/* 所以在这里使用强制转换就会造成拦截器无效 */

	// 接收新密码
	private String newPassword;
	// 定义一个二维数组，来接收inform的值
	private String inform[] = { "0", "0" };
	private RehabExperience rehabExperience;
	private Message message;
	private Message replyMsg; // 回复时建立的临时邮件实例

	/**
	 * 跳转到患者中心
	 * 
	 * @return
	 * @throws Exception
	 */
	public String center() throws Exception {
		getMessagesCount();
		getTodayRehabPlanCount();
		return "patientCenter";
	}

	/**
	 * 显示用户的个人资料，并且可以用于修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String basicInfoUI() throws Exception {
		getMessagesCount();
		// 1. 准备回显的数据 user已经保存到session中，可以直接读取
		model = getPatient();
		ActionContext.getContext().getValueStack().push(model);
		return "basicInfoUI";
	}

	/**
	 * 更新个人资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateBasicInfo() throws Exception {
		getMessagesCount();
		// 注意这里更新的数据并不多，如果不用 <s:input type="hidden" /> 来将所有数据不全的话，更新时就会造成数据缺失
		// 即这里得到的model 是不全的信息，所以这里采取的措施是重写一个update更新函数，只更新出现的基本信息

		// 方案：注意现在session中存储了user
		// 可以直接使用user.set()来修改user的信息，在session关闭后，就会保存到数据库中去
		// 如果想要立即更新，则可以新建一个patient对象，将user赋给patient，进行更新？？？

		Patient user = getPatient();
		// 设置更新的数据
		user.setTrueName(model.getTrueName());
		user.setGender(model.getGender());
		user.setBirthday(model.getBirthday());
		user.setCity(model.getCity());
		user.setEmail(model.getEmail());
		user.setTelephone(model.getTelephone());
		user.setWeixin(model.getWeixin());

		// 立即更新
		patientService.update(user);
		return "toBasicInfoUI";
	}

	/**
	 * 修改用户密码页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePasswordUI() throws Exception {
		getMessagesCount();
		return "changePasswordUI";
	}

	/**
	 * 更新密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePassword() throws Exception {
		getMessagesCount();
		// 1. 验证原密码是否正确
		Patient user = (Patient) getCurrentUser();
		// 不正确
		if (!DigestUtils.md5Hex(model.getPassword()).equals(user.getPassword())) {
			addFieldError("errorMsg", "原密码输入错误！");
		} else {
			// 正确，更新密码
			// patientService.changePwd(newPassword,(Patient)getCurrentUser());
			user.setPassword(DigestUtils.md5Hex(newPassword));

			// 立即更新
			patientService.update(user);
			addFieldError("errorMsg", "密码修改成功！");
		}
		return "changePasswordUI";
	}

	/**
	 * 积分管理的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String creditsManageUI() throws Exception {
		getMessagesCount();
		return "creditsManageUI";
	}

	/**
	 * 短信通知设置页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String messageSetupUI() throws Exception {
		getMessagesCount();
		Patient user = (Patient) getCurrentUser();
		System.out.println(user.getInformWay());
		int informWay = user.getInformWay();
		switch (informWay) {
		case 0:
			inform[0] = "false";
			inform[1] = "false";
			break;
		case 1:
			inform[0] = "true";
			inform[1] = "false";
			break;
		case 2:
			inform[0] = "false";
			inform[1] = "true";
			break;
		case 3:
			inform[0] = "true";
			inform[1] = "true";
			break;
		}
		return "messageSetupUI";
	}

	/**
	 * 设置短信的通知方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public String messageSetup() throws Exception {
		Patient user = (Patient) getCurrentUser();
		System.out.println("inform[0]=" + inform[0]);
		System.out.println("inform[1]=" + inform[1]);
		if (inform[0].equals("false")) {
			inform[0] = "0";
		} else if (inform[1].equals("false")) {
			inform[1] = "0";
		}
		user.setInformWay(Integer.parseInt(inform[0])
				+ Integer.parseInt(inform[1]));
		// 立即更新
		patientService.update(user);
		return "toMessageSetupUI";
	}

	/**
	 * 病历
	 * 
	 * @return
	 * @throws Exception
	 */
	public String myMedicalRecordUI() throws Exception {
		getMessagesCount();
		// 病历的数据 搞清楚病历包含的内容可以单独做一张表
		return "myMedicalRecordUI";
	}

	/**
	 * 我的医生页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String myDoctorUI() throws Exception {
		getMessagesCount();
		// 数据已经准备好
		return "myDoctorUI";

	}

	/**
	 * 我的医保情况页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String myHealthInsuranceUI() throws Exception {
		getMessagesCount();
		return "myHealthInsuranceUI";
	}

	/**
	 * 我的康复经验页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String rehabExperienceList() throws Exception {
		getMessagesCount();
		// 从数据库中拿出康复经验的列表
		// List<RehabExperience> rehabExperienceList = rehabExperienceService
		// .findAllByAuthorId(getPatient().getId());
		// ActionContext.getContext().put("rehabExperienceList",
		// rehabExperienceList);

		// 分页, 按照时间顺序倒序
		new QueryHelper(RehabExperience.class, "r")
				.addCondition("r.author=?", getPatient())
				.addOrderProperty("r.postTime", false)
				.preparePageBean(rehabExperienceService, pageNow, 12);

		return "rehabExperienceList";
	}

	/**
	 * 显示康复经验的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String rehabExperienceShowUI() throws Exception {
		getMessagesCount();
		// 准备数据
		rehabExperience = rehabExperienceService.getById(rehabExperience
				.getId());
		ActionContext.getContext().put("rehabExperience", rehabExperience);
		return "rehabExperienceShowUI";

	}

	/**
	 * 删除康复经验操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRehabExperience() throws Exception {
		getMessagesCount();
		rehabExperienceService.deleteById(rehabExperience.getId());
		return "toRehabExperienceList";

	}

	/**
	 * 添加康复经验的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRehabExperienceUI() throws Exception {
		getMessagesCount();
		return "addRehabExperienceUI";
	}

	/**
	 * 添加康复经验
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRehabExperience() throws Exception {
		getMessagesCount();
		// 添加康复经验到数据库
		rehabExperience.setPostTime(new Date());
		rehabExperience.setAuthor(getPatient());
		rehabExperienceService.save(rehabExperience);
		return "toRehabExperienceList";

	}

	/**
	 * 今日计划页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String todayRehabPlanUI() throws Exception {
		getMessagesCount();
		Patient user = getPatient();
		// 准备今日计划的数据
		List<RehabPlan> todayRehabPlansList = rehabPlanService
				.getTodayRehabPlansByPatient(user.getId());

		ActionContext.getContext().put("todayRehabPlansList",
				todayRehabPlansList);
		return "todayRehabPlanUI";
	}

	/**
	 * 已完成计划页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String completedRehabPlansList() throws Exception {
		getMessagesCount();

		// 准备已完成计划的数据
		// List<RehabPlan> completedPlansList = rehabPlanService
		// .getCompletedRehabPlansByPatient(user.getId());
		//
		// ActionContext.getContext()
		// .put("completedPlansList", completedPlansList);
		// 分页
		new QueryHelper(RehabPlan.class, "r")
				.addCondition("r.patient=?", getPatient())
				.addCondition("r.completed=?", false)
				.addOrderProperty("r.time", false)
				.preparePageBean(rehabPlanService, pageNow, 20);

		return "completedRehabPlansList";

	}

	/**
	 * 未完成计划页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String notCompletedRehabPlansList() throws Exception {
		getMessagesCount();

		// Patient user = getPatient();
		// // 准备未完成计划的数据
		// List<RehabPlan> notCompletedPlansList = rehabPlanService
		// .getNotCompletedRehabPlansByPatient(user.getId());
		//
		// ActionContext.getContext().put("notCompletedPlansList",
		// notCompletedPlansList);
		// 分页
		new QueryHelper(RehabPlan.class, "r")
				.addCondition("r.patient=?", getPatient())
				.addCondition("r.completed=?", true)
				.addOrderProperty("r.time", false)
				.preparePageBean(rehabPlanService, pageNow, 20);

		return "notCompletedRehabPlansList";

	}

	/**
	 * 发件箱
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendBoxList() throws Exception {
		getMessagesCount();
		// 显示患者已经发送的信息
		// List<Message> sendBoxList = messageService.getAllByFromUserId(
		// getPatient().getId(), false);
		// ActionContext.getContext().put("sendBoxList", sendBoxList);
		// ActionContext.getContext().put("size", sendBoxList.size());

		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.fromUser=?", getPatient())
				.addCondition("m.deleteBySender=?", false)
				.addOrderProperty("m.postTime", false)
				.preparePageBean(messageService, pageNow, 12);

		return "sendBoxList";
	}

	/**
	 * 收件箱
	 * 
	 * @return
	 * @throws Exception
	 */
	public String receiveBoxList() throws Exception {
		getMessagesCount();
		// 查询发给患者的所有信息，包括系统信息和医生信息
		// List<Message> receiveBoxList = messageService.getAllByToUserId(
		// getPatient().getId(), false);
		// ActionContext.getContext().put("receiveBoxList", receiveBoxList);
		// ActionContext.getContext().put("size", receiveBoxList.size());
		
		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.toUser=?", getPatient())
				.addCondition("m.deleteByReceiver=?", false)
				.addOrderProperty("m.postTime", false)
				.preparePageBean(messageService, pageNow, 12);
		return "receiveBoxList";
	}

	/**
	 * 发送邮件的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendMessageUI() throws Exception {
		getMessagesCount();
		if (null != replyMsg) {
			replyMsg = messageService.getById(replyMsg.getId());
		}
		List<User> receiversList = new ArrayList<User>();
		receiversList.add(getPatient().getDoctor());
		receiversList.add(userService.getByLoginName("mreAdmin"));
		ActionContext.getContext().put("receiversList", receiversList);
		ActionContext.getContext().put("replyMsg", replyMsg);

		return "sendMessageUI";
	}

	/**
	 * 添加邮件到数据库
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendMessage() throws Exception {
		getMessagesCount();
		// 如果是由快捷回复直接发送邮件，则应该将临时对象replyMsg赋值给message
		if (null == message && replyMsg != null) {
			message = replyMsg;
		}
		message.setFromUser(getPatient());
		// 收件人
		message.setPostTime(new Date());
		messageService.save(message);

		return "toSendBoxList";
	}

	/**
	 * 显示发件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendMessageShowUI() throws Exception {
		getMessagesCount();
		message = messageService.getById(message.getId());
		ActionContext.getContext().put("message", message);
		return "sendMessageShowUI";
	}

	/**
	 * 删除已发邮件 注意这里的删除操作，只是发件人这里不显示了，收件人依然可以看到
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteSendMessage() throws Exception {
		getMessagesCount();
		message = messageService.getById(message.getId());
		message.setDeleteBySender(true);
		messageService.update(message);
		return "toSendBoxList";
	}

	/**
	 * 删除已收邮件 注意这里的删除操作，只是收件人这里不显示了，发件人依然可以看到
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteReceiveMessage() throws Exception {
		getMessagesCount();
		message = messageService.getById(message.getId());
		message.setDeleteByReceiver(true);
		messageService.update(message);
		return "toReceiveBoxList";
	}

	/**
	 * 读取邮件内容，将readByReceiver设置成true，并更新
	 * 
	 * @return
	 * @throws Exception
	 */
	public String receiveMessageShowUI() throws Exception {
		getMessagesCount();
		message = messageService.getById(message.getId());
		message.setReadByReceiver(true);
		messageService.update(message);
		ActionContext.getContext().put("message", message);
		return "receiveMessageShowUI";
	}

	/** -------------------- public method -------------------- **/

	private Patient getPatient() {
		return (Patient) getCurrentUser();
	}

	/** -------------------- Getter And Setter -------------------------- **/
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String[] getInform() {
		return inform;
	}

	public void setInform(String[] inform) {
		this.inform = inform;
	}

	public RehabExperience getRehabExperience() {
		return rehabExperience;
	}

	public void setRehabExperience(RehabExperience rehabExperience) {
		this.rehabExperience = rehabExperience;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Message getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(Message replyMsg) {
		this.replyMsg = replyMsg;
	}
}
