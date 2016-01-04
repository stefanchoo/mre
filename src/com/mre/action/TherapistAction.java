package com.mre.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.Doctor;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.Message;
import com.mre.domain.RehabPlan;
import com.mre.domain.Therapist;
import com.mre.domain.User;
import com.mre.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TherapistAction extends BaseAction<Therapist> {

	private static final long serialVersionUID = -5807618522050190477L;

	private String newPassword;
	// 定义一个二维数组，来接收inform的值
	private String inform[] = { "0", "0" };

	private String type;

	// 查询的关键词
	private EquipmentUsed equipment;
	private Date fromDate;
	private Date toDate;

	private Message message; // 普通邮件
	private Message replyMsg; // 回复邮件

	// 康复计划完成状态数据
	private String completedStatus;

	/**
	 * 跳转到医生中心
	 * 
	 * @return
	 * @throws Exception
	 */
	public String center() throws Exception {
		getMessagesCount();
		getTodayRehabPlanCount();
		return "therapistCenter";
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
		model = (Therapist) getCurrentUser();
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
		// ？？？注意现在session中存储了user
		// 可以直接使用user.set()来修改user的信息，在session关闭后，就会保存到数据库中去
		// 如果想要立即更新，则可以新建一个patient对象，将user赋给patient，进行更新？？？

		Therapist user = getTherapist();
		// 设置更新的数据
		user.setTrueName(model.getTrueName());
		user.setGender(model.getGender());
		user.setBirthday(model.getBirthday());
		user.setCity(model.getCity());
		user.setEmail(model.getEmail());
		user.setTelephone(model.getTelephone());
		user.setWeixin(model.getWeixin());
		therapistService.update(user);

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

	public String changePassword() throws Exception {
		getMessagesCount();
		// 1. 验证原密码是否正确
		Therapist user = getTherapist();
		// 不正确
		if (!DigestUtils.md5Hex(model.getPassword()).equals(user.getPassword())) {
			addFieldError("errorMsg", "原密码输入错误！");
		} else {
			// 正确，更新密码
			// patientService.changePwd(newPassword,(Patient)getCurrentUser());
			user.setPassword(DigestUtils.md5Hex(newPassword));
			therapistService.update(user);
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
		int informWay = getTherapist().getInformWay();
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
		getMessagesCount();
		Therapist user = getTherapist();
		System.out.println("inform[0]=" + inform[0]);
		System.out.println("inform[1]=" + inform[1]);
		if (inform[0].equals("false")) {
			inform[0] = "0";
		} else if (inform[1].equals("false")) {
			inform[1] = "0";
		}
		user.setInformWay(Integer.parseInt(inform[0])
				+ Integer.parseInt(inform[1]));
		therapistService.update(user);
		return "toMessageSetupUI";
	}

	/**
	 * 设备列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipmentsList() throws Exception {
		getMessagesCount();
		// TODO: 一台设备只能由一个治疗师负责吗？ 会不会出现一台设备多人负责的情况 ？？？ 需调研
		// List<EquipmentUsed> equipmentUsedsList = equipmentUsedService
		// .findAllByTherapistId(getTherapist().getId());
		// ActionContext.getContext()
		// .put("equipmentUsedsList", equipmentUsedsList);

		new QueryHelper(EquipmentUsed.class, "e")//
				.addCondition("e.therapist=?", getTherapist())//
				.preparePageBean(equipmentUsedService, pageNow, 20);
		return "equipmentsList";
	}

	/**
	 * 进入设备控制首页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipmentShowUI() throws Exception {
		getMessagesCount();
		EquipmentUsed equipmentUsed = equipmentUsedService.getById(equipment
				.getId());
		String returnName = getEquipReturnName(equipmentUsed);
		return returnName + "_index";
	}

	/**
	 * 显示今日计划，算法就是查询训练时间为今天，医生为本人的rehabList
	 * 
	 * @return
	 * @throws Exception
	 */
	public String todayRehabPlanUI() throws Exception {
		getMessagesCount();
		// 使用Date(time[字段]) = curdate() 来进行查询
		List<RehabPlan> rehabPlansList = rehabPlanService
				.getTodayRehabPlansByTherapist(getTherapist());
		ActionContext.getContext().put("rehabPlansList", rehabPlansList);
		return "todayRehabPlanUI";

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
		// getTherapist().getId(), false);
		// ActionContext.getContext().put("sendBoxList", sendBoxList);
		// ActionContext.getContext().put("size", sendBoxList.size());
		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.fromUser=?", getTherapist())
				.addCondition("m.deleteBySender=?", false)
				.addOrderProperty("m.postTime", false)
				.preparePageBean(messageService, pageNow, 20);

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
		// getTherapist().getId(), false);
		// ActionContext.getContext().put("receiveBoxList", receiveBoxList);
		// ActionContext.getContext().put("size", receiveBoxList.size());

		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.toUser=?", getTherapist())
				.addCondition("m.deleteByReceiver=?", false)
				.addOrderProperty("m.postTime", false)
				.preparePageBean(messageService, pageNow, 20);
		return "receiveBoxList";
	}

	/**
	 * 发送邮件的页面，准备收件人列表 本院康复医生的列表 + admin
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
		List<Doctor> doctorsList = doctorService
				.findAllByHospitalId(getTherapist().getHospital().getId());
		for (Doctor d : doctorsList) {
			receiversList.add(d);
		}
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
		message.setFromUser(getTherapist());
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

	public Therapist getTherapist() {
		return (Therapist) getCurrentUser();
	}

	/** -------------------- Getter And Setter -------------------- **/
	public String[] getInform() {
		return inform;
	}

	public void setInform(String[] inform) {
		this.inform = inform;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EquipmentUsed getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentUsed equipment) {
		this.equipment = equipment;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(String completedStatus) {
		this.completedStatus = completedStatus;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
