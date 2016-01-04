package com.mre.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.Doctor;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.Message;
import com.mre.domain.Patient;
import com.mre.domain.RehabPlan;
import com.mre.domain.Role;
import com.mre.domain.Therapist;
import com.mre.domain.User;
import com.mre.util.Formatter;
import com.mre.util.QueryHelper;
import com.mre.util.domain.PageBean;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DoctorAction extends BaseAction<Doctor> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7143740292174205692L;
	private String newPassword;
	// 定义一个二维数组，来接收inform的值
	private String inform[] = { "0", "0" };

	private String type;

	// 康复计划
	private RehabPlan rehabPlan = new RehabPlan();

	// 患者
	private Patient patient;

	// 查询的关键词
	private EquipmentUsed equipment;
	private Date fromDate;
	private Date toDate;

	// 康复计划完成状态数据
	private String completedStatus;
	private String rehabPlanTime;

	private Message message;
	private Message replyMsg; // 回复时建立的临时邮件实例

	/**
	 * 跳转到医生中心
	 * 
	 * @return
	 * @throws Exception
	 */
	public String center() throws Exception {
		getMessagesCount();
		getTodayRehabPlanCount();
		return "doctorCenter";
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
		model = getDoctor();
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
		// 注意这里更新的数据并不多，如果不用 <s:input type="hidden" /> 来将所有数据不全的话，更新时就会造成数据缺失
		// 即这里得到的model 是不全的信息，所以这里采取的措施是重写一个update更新函数，只更新出现的基本信息
		// ？？？注意现在session中存储了user
		// 可以直接使用user.set()来修改user的信息，在session关闭后，就会保存到数据库中去
		// 如果想要立即更新，则可以新建一个patient对象，将user赋给patient，进行更新？？？
		Doctor user = getDoctor();
		// 设置更新的数据
		user.setTrueName(model.getTrueName());
		user.setGender(model.getGender());
		user.setBirthday(model.getBirthday());
		user.setCity(model.getCity());
		user.setEmail(model.getEmail());
		user.setTelephone(model.getTelephone());
		user.setWeixin(model.getWeixin());
		doctorService.update(user);

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
		Doctor user = getDoctor();
		// 不正确
		if (!DigestUtils.md5Hex(model.getPassword()).equals(user.getPassword())) {
			addFieldError("errorMsg", "原密码输入错误！");
		} else {
			// 正确，更新密码
			// patientService.changePwd(newPassword,(Patient)getCurrentUser());
			user.setPassword(DigestUtils.md5Hex(newPassword));
			doctorService.update(user);
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
		Doctor user = getDoctor();
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
		Doctor user = (Doctor) getCurrentUser();
		System.out.println("inform[0]=" + inform[0]);
		System.out.println("inform[1]=" + inform[1]);
		if (inform[0].equals("false")) {
			inform[0] = "0";
		} else if (inform[1].equals("false")) {
			inform[1] = "0";
		}
		user.setInformWay(Integer.parseInt(inform[0])
				+ Integer.parseInt(inform[1]));
		doctorService.update(user);
		return "toMessageSetupUI";
	}

	/**
	 * 
	 * @throws Exception
	 */
	public String patientsList() throws Exception {
		getMessagesCount();

		// 1. 准备数据Patients的数据
		// List<Patient> patientsList =
		// patientService.findAllByDoctorId(user.getId());
		QueryHelper queryHelper = new QueryHelper(Patient.class, "p")//
				.addCondition("p.doctor=?", getDoctor()) //
				.preparePageBean(patientService, pageNow, 20);
		
		PageBean pageBean = patientService.getPageBean(pageNow, 20, queryHelper);

		
		// 2. 准备患者今日计划的List列表
		List<Integer> todayRehabPlansList = new ArrayList<Integer>();

		for (Object o : pageBean.getRecordList()) {
			Patient p = new Patient();
			if(o instanceof Patient) {
			   p = (Patient)o;
			}
			int todayRehabPlanCount = rehabPlanService.getTodayRehabPlanCount(
					"patient", p);
			todayRehabPlansList.add(todayRehabPlanCount);
		}
		// ActionContext.getContext().put("patientsList", patientsList);
		ActionContext.getContext().put("todayRehabPlansList",
				todayRehabPlansList);

		return "patientsList";
	}

	/**
	 * 删除患者、注意这个只是取消管理关系
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletePatient() throws Exception {
		// 1. 得到要删除Patient的id
		// 2. 取消与该患者的关联关系
		// 3. 取消与该患者相关的训练计划
		patient = patientService.getById(patient.getId());
		patient.setDoctor(null);
		patientService.update(patient);

		System.out.println("病人删除成功");
		return "toPatientsList";
	}

	/**
	 * 跳转到添加患者的页面，将控制提交操作的类型type---> add:添加 bind:绑定 <--- 情况1：
	 * 不进行搜索，直接进行添加，此时type=add; 验证用户名是否存在？ 存在返回addPatientUI页面，修改后添加 情况2： 搜索->
	 * 不存在该用户， 此时type=add; 后面的情况与情况1一致 情况3： 搜索-> 存在该用户， 此时type=bind;
	 * 此时在addpatentUI页面，医生不能修改患者的信息，取消按钮也失效，只能点击添加
	 * 
	 * @return
	 * @throws Exception
	 */

	public String addPatientUI() throws Exception {
		getMessagesCount();
		// 默认要提交的是 添加操作
		ActionContext.getContext().getSession().put("type", "add");
		return "addPatientUI";
	}

	/**
	 * 医生在添加患者时，搜索已经注册的患者
	 * 
	 * @return
	 * @throws Exception
	 */
	public String validatePatient() throws Exception {
		getMessagesCount();
		// ----------------- 第一种直接传值的方式 -------------------------//
		/*
		 * 以get 方式传递过来的数据，暂时以getRequest().getParameter() String ploginName =
		 * (String)
		 * ServletActionContext.getRequest().getParameter("ploginName"); String
		 * ptrueName = (String)
		 * ServletActionContext.getRequest().getParameter("ptrueName"); //
		 * 这里还需要处理中文乱码的问题
		 * System.out.println(ploginName+"|"+Formatter.getNewString(ptrueName));
		 * Patient patient =
		 * patientService.findByLoginNameAndTrueName(ploginName
		 * ,Formatter.getNewString(ptrueName));
		 */
		// ---------------------- 第二种使用对象来接收 ----------------------//
		patient = patientService.findByLoginNameAndTrueName(
				patient.getLoginName(),
				Formatter.getNewString(patient.getTrueName()));
		if (patient == null) {
			addFieldError("errorMsg", "您搜索的用户不存在！");
		} else {
			// 将type设置为"bind"-- 绑定操作
			ActionContext.getContext().getSession().remove("type");
			ActionContext.getContext().getSession().put("type", "bind");
			addFieldError("msg", "已为您找到用户,点击添加即可！");
		}
		// 放在栈顶
		ActionContext.getContext().getValueStack().push(patient);
		return "addPatientUI";
	}

	/**
	 * 其中已经注册过的患者可以通过搜索用户名来得到，没有则需要添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPatient() throws Exception {
		getMessagesCount();
		Doctor user = getDoctor();
		Set<Role> roles = new HashSet<Role>();
		Role role = roleService.getByName("患者");
		roles.add(role);
		System.out.println("type=" + type);
		if (type.equals("add")) {
			// 先判断用户名是否存在？
			if (userService.findByLoginName(patient.getLoginName())) {
				addFieldError("errorMsg", "用户名已经存在，请更换一个用户名！");
				return "addPatientUI";
			}
			// 添加并绑定用户, 由于这些都是patient和doctor共有的基本信息，所以可以直接使用model对象来接收数据
			patient.setRegisterTime(new Date());
			patient.setIcon("default.png");
			patient.setDoctor(user);
			patient.setHospital(user.getHospital());
			patient.setRoles(roles);
			patientService.save(patient);
			System.out.println("患者添加并绑定成功！");
		} else if (type.equals("bind")) {
			// 绑定已存在的用户
			patient = patientService.findByLoginNameAndTrueName(
					patient.getLoginName(), patient.getTrueName());
			patient.setDoctor(user);
			patientService.update(patient);
			System.out.println("患者绑定成功！");
		}
		ActionContext.getContext().getSession().remove("type");
		return "toPatientsList";
	}

	/**
	 * 查看病人的信息 = 病历+训练效果+私信
	 * 
	 * @return
	 * @throws Exception
	 */
	public String patientShowUI() throws Exception {
		getMessagesCount();
		// 根据得到的patientId 来准备病历数据和训练效果图
		return "patientShowUI";
	}

	/**
	 * 康复训练计划列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String rehabPlansList() throws Exception {
		getMessagesCount();
		// 1. 准备出医生制定的所有康复计划,以默认以时间顺序显示
		// 2. 准备患者数据和使用的设备数据
		Doctor user = getDoctor();
		List<EquipmentUsed> equipmentUsedsList = equipmentUsedService
				.findAllByHospitalId(user.getHospital().getId());
		ActionContext.getContext()
				.put("equipmentUsedsList", equipmentUsedsList);

		List<Patient> patientsList = patientService.findAllByDoctorId(user
				.getId());
		ActionContext.getContext().put("patientsList", patientsList);

		// 有筛选的情况下 筛选条件为患者 设备 时间从 .. 时间到 .. 时间
		// 每页显示8条记录
		new QueryHelper(RehabPlan.class, "r")
				.addCondition("r.doctor=?", user)
				.addCondition((patient != null) && (patient.getId() != 0),
						"r.patient=?", patient)
				.addCondition((equipment != null) && (equipment.getId() != 0),
						"r.equipment=?", equipment)
				.addCondition(fromDate != null, "r.time > ?", fromDate)
				.addCondition(toDate != null, "r.time < ?", toDate)
				.addOrderProperty("r.time", false)
				.preparePageBean(rehabPlanService, pageNow, 8);

		// 根据筛选条件得到rehabPlanList
		// List<RehabPlan> rehabPlansList =
		// rehabPlanService.findByHQL(queryHelper.getHqlQuery(),queryHelper.getParameters());
		// ActionContext.getContext().put("rehabPlansList", rehabPlansList);
		return "rehabPlansList";
	}

	/**
	 * 删除该训练计划, 取消关联与医生的关系(考虑到治疗师查看的时候引起错误) 两种情况： 已完成:取消关系，未完成:删除 (删除的级联关系要设置上)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRehabPlan() throws Exception {
		System.out.println(rehabPlan.getId());
		if (rehabPlan.isCompleted()) {
			rehabPlan = rehabPlanService.getById(rehabPlan.getId());
			rehabPlan.setDoctor(null);
			rehabPlanService.update(rehabPlan);
		} else {
			rehabPlanService.deleteById(rehabPlan.getId());
		}
		return "toRehabPlansList";

	}

	/**
	 * 显示该训练计划的情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public String rehabPlanShowUI() throws Exception {
		getMessagesCount();
		// 准备数据
		// 1. >> 训练计划信息
		// 2. >> 训练设备列表
		// 3. >> 训练类型列表
		Doctor user = getDoctor();
		List<EquipmentUsed> equipmentUsedsList = equipmentUsedService
				.findAllByHospitalId(user.getHospital().getId());
		ActionContext.getContext()
				.put("equipmentUsedsList", equipmentUsedsList);

		ActionContext.getContext().put("typeList", getRehabPlanTypeList());

		rehabPlan = rehabPlanService.getById(rehabPlan.getId());
		ActionContext.getContext().getValueStack().push(rehabPlan);

		return "rehabPlanShowUI";

	}

	/**
	 * 更新康复训练计划的信息 先取出对象，让对象在session中才能进行更新
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateRehabPlan() throws Exception {
		Date timeDate = Formatter.getDateFromString(rehabPlanTime);
		System.out.println("时间：" + timeDate);
		// 找到设备添加
		EquipmentUsed e = equipmentUsedService.getById(rehabPlan.getEquipment()
				.getId());
		rehabPlan.setEquipment(e);
		// 先取出被修改的rehabPlan对象
		RehabPlan updateRehabPlan = rehabPlanService.getById(rehabPlan.getId());
		// 将原rehabPlan赋值给他
		updateRehabPlan.setName(rehabPlan.getName());
		updateRehabPlan.setContent(rehabPlan.getContent());
		updateRehabPlan.setTime(timeDate);
		updateRehabPlan.setType(rehabPlan.getType());
		updateRehabPlan.setExerciseTime(rehabPlan.getExerciseTime());
		updateRehabPlan.setEquipment(rehabPlan.getEquipment());
		updateRehabPlan.setResult(rehabPlan.getResult());

		// 修改 设备的 TrainingType
		updateEquipTrainningType(updateRehabPlan);
		// 保存信息,处理一下时间, 完成状态
		if (completedStatus.equals("未完成")) {
			updateRehabPlan.setCompleted(false);
		} else if (completedStatus.equals("已完成")) {
			updateRehabPlan.setCompleted(true);
		}

		rehabPlanService.update(updateRehabPlan);
		return "toRehabPlanShowUI";
	}

	/**
	 * 制定计划页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRehabPlanUI() throws Exception {
		getMessagesCount();
		// 准备数据
		// 1. >> 训练设备列表
		// 2. >> 训练类型列表
		// 3. >> 患者列表
		Doctor user = getDoctor();
		List<EquipmentUsed> equipmentUsedsList = equipmentUsedService
				.findAllByHospitalId(user.getHospital().getId());
		ActionContext.getContext()
				.put("equipmentUsedsList", equipmentUsedsList);

		ActionContext.getContext().put("typeList", getRehabPlanTypeList());

		List<Patient> patientsList = patientService.findAllByDoctorId(user
				.getId());
		ActionContext.getContext().put("patientsList", patientsList);
		return "addRehabPlanUI";
	}

	/**
	 * 制定康复计划 问题来啦，关联的设备只有id,
	 * 虽然这样可以在下次显示的时候根据id找到，但是这个时候要添加一个设备的计划就会找不到equipmentUsed的number 
	 * 解决：
	 * 根据id找到设备重新添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRehabPlan() throws Exception {
		Doctor user = getDoctor();
		// 找到设备添加
		EquipmentUsed e = equipmentUsedService.getById(rehabPlan.getEquipment()
				.getId());
		rehabPlan.setEquipment(e);

		// String -> Date
		Date timeDate = Formatter.getDateFromString(rehabPlanTime);
		rehabPlan.setTime(timeDate);
		// 保存康复计划的信息及关联关系
		rehabPlan.setDoctor(user);
		rehabPlanService.save(rehabPlan);
		System.out.println("当前的康复计划的id为" + rehabPlan.getId());
		// 得到选择的是那款设备，要添加一个空的设备的康复计划，并且要关联起来
		saveEquipRehabPlanByRehabPlan(rehabPlan);
		return "toRehabPlansList";
	}

	/**
	 * 设置具体哪个设备的计划定制页面，调出参数设置的页面 1. 在添加时，不需要设置详细参数，但是会根据设备生成一个对应的计划定制表 2. 在更新时
	 * 可以设置详细参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String setEquipmentParams() throws Exception {
		
		rehabPlan = rehabPlanService.getById(rehabPlan.getId());
		String returnName = getEquipReturnName(rehabPlan.getEquipment());
		return returnName + "_makePlansUI";
	}

	/**
	 * 显示今日计划，算法就是查询训练时间为今天，医生为本人的rehabList
	 * 
	 * @return
	 * @throws Exception
	 */
	public String todayRehabPlanUI() throws Exception {
		getMessagesCount();
		Doctor user = getDoctor();
		// 使用Date(time[字段]) = curdate() 来进行查询
		List<RehabPlan> rehabPlansList = rehabPlanService
				.getTodayRehabPlansByDoctor(user.getId());
		ActionContext.getContext().put("rehabPlansList", rehabPlansList);
		return "todayRehabPlanUI";

	}

	/**
	 * 设备列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipmentsList() throws Exception {
		getMessagesCount();
		// Doctor user = getDoctor();
		// List<EquipmentUsed> equipmentUsedsList =
		// equipmentUsedService.findAllByHospitalId(user.getHospital().getId());
		// ActionContext.getContext().put("equipmentUsedsList",
		// equipmentUsedsList);

		new QueryHelper(EquipmentUsed.class, "e").addCondition("e.hospital=?",
				getDoctor().getHospital()).preparePageBean(
				equipmentUsedService, pageNow, 20);

		return "equipmentsList";
	}

	/**
	 * 进入设备控制首页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipmentShowUI() throws Exception {
		EquipmentUsed equipmentUsed = equipmentUsedService.getById(equipment
				.getId());
		String returnName = getEquipReturnName(equipmentUsed);
		return returnName + "_index";
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
		// getDoctor().getId(), false);
		// ActionContext.getContext().put("sendBoxList", sendBoxList);
		// ActionContext.getContext().put("size", sendBoxList.size());

		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.fromUser=?", getDoctor())
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
		// getDoctor().getId(), false);
		// ActionContext.getContext().put("receiveBoxList", receiveBoxList);
		// ActionContext.getContext().put("size", receiveBoxList.size());

		// 分页显示
		new QueryHelper(Message.class, "m")
				.addCondition("m.toUser=?", getDoctor())
				.addCondition("m.deleteByReceiver=?", false)
				.addOrderProperty("m.postTime", false)
				.preparePageBean(messageService, pageNow, 20);

		return "receiveBoxList";
	}

	/**
	 * 发送邮件的页面，准备收件人列表 receiversList 病人列表 + admin + 治疗师
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
		List<Patient> patientsList = patientService
				.findAllByDoctorId(getDoctor().getId());
		for (Patient p : patientsList) {
			receiversList.add(p);
		}
		List<Therapist> therapistsList = therapistService
				.findAllByHospitalId(getDoctor().getHospital().getId());
		for (Therapist t : therapistsList) {
			receiversList.add(t);
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
		message.setFromUser(getDoctor());
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
	private Doctor getDoctor() {
		return (Doctor) getCurrentUser();
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

	public RehabPlan getRehabPlan() {
		return rehabPlan;
	}

	public void setRehabPlan(RehabPlan rehabPlan) {
		this.rehabPlan = rehabPlan;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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

	public String getRehabPlanTime() {
		return rehabPlanTime;
	}

	public void setRehabPlanTime(String rehabPlanTime) {
		this.rehabPlanTime = rehabPlanTime;
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
