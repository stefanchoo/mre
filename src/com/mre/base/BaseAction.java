package com.mre.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mre.domain.CentrobotRehabPlan;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.RehabPlan;
import com.mre.domain.User;
import com.mre.service.CentrobotDataService;
import com.mre.service.CentrobotRehabPlanService;
import com.mre.service.DoctorService;
import com.mre.service.EquipmentUsedService;
import com.mre.service.MessageService;
import com.mre.service.PatientService;
import com.mre.service.RehabExperienceService;
import com.mre.service.RehabPlanService;
import com.mre.service.RoleService;
import com.mre.service.TherapistService;
import com.mre.service.UserService;
import com.mre.util.domain.SaveBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 使用泛型提取公共的的代码得到baseAction<T> 实现modelDriven<T>
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6034816489353634122L;

	/* ====== 分页使用到的参数 ===== */
	protected int pageSize = 10; // 每页显示多少条
	protected int pageNow = 1; // 当前为第几页

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	/**
	 * ============== 获取model的实例 ==============
	 */
	protected T model;

	@SuppressWarnings("unchecked")
	public BaseAction() {
		try {
			// 通过反射得到model的真实类型
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	@Override
	public T getModel() {
		return model;
	}

	/**
	 * 获取当前登录的用户, 因为用户不知道是哪种身份
	 * 
	 * @return
	 */
	protected SaveBean saveBean = new SaveBean();

	protected SaveBean getSaveBean() {
		return (SaveBean) ActionContext.getContext().getSession()
				.get("saveBean");
	}

	protected Object getCurrentUser() {
		return ActionContext.getContext().getSession().get("user");
	}

	/**
	 * 获取今日计划个数
	 */
	protected void getTodayRehabPlanCount() {
		int todayRehabPlanCount = rehabPlanService.getTodayRehabPlanCount(
				getSaveBean().getDiscriminator(), getCurrentUser());
		ActionContext.getContext().put("todayRehabPlanCount",
				todayRehabPlanCount);
	}
	
	/**
	 * 获取消息个数
	 */
	protected void getMessagesCount() {
		int messagesCount = 0;
		if(null != getCurrentUser()){
		 messagesCount = messageService.getMessageCountByToUserId(
				((User) getCurrentUser()).getId(), false, false);
		} 	
		ActionContext.getContext().put("messagesCount", messagesCount+"");
	}

	/********* 构建一个训练计划类型的 List ***********/
	// TODO: 这个后期可能也会用数据库的方式存储
	protected List<String> getRehabPlanTypeList() {
		List<String> typeList = new ArrayList<String>();
		typeList.add("手部康复被动训练");
		typeList.add("手部康复主动训练");

		typeList.add("上肢康复被动训练");
		typeList.add("上肢康复主动训练");

		typeList.add("下肢康复被动训练");
		typeList.add("下肢康复主动训练");

		return typeList;
	}

	/**
	 * 根据康复计划和设备编号，添加一个设备计划
	 * 
	 * @param r
	 * @param number
	 */
	protected void saveEquipRehabPlanByRehabPlan(RehabPlan r) {
		// 得到训练模式
		String type = r.getType().split("康复")[1];
		String number = r.getEquipment().getNumber();
		System.out.println(number);
		// 如果是centrobot
		if (number.startsWith("sz-centrobot")) {
			CentrobotRehabPlan centrobotRehabPlan = new CentrobotRehabPlan();
			centrobotRehabPlan.setTrainingType(type);
			centrobotRehabPlan.setRehabPlan(r);
			centrobotRehabPlanService.save(centrobotRehabPlan);
		}
		// 如果是rehand
		// ...
	}

	/**
	 * 根据设备选择进入设备控制页面
	 * 
	 * @param e
	 * @return
	 */
	protected String getEquipReturnName(EquipmentUsed e) {
		String equipName = "";
		String number = e.getNumber();
		if (number.startsWith("sz-centrobot")) {
			equipName = "Centrobot";
		}
		return "to" + equipName;
	}

	/**
	 * 根据康复训练计划设置子计划训练类型
	 */
	protected void updateEquipTrainningType(RehabPlan r) {
		String type = r.getType().split("复")[1];
		if (r.getEquipment().getNumber().equals("sz-centrobot")) {
			CentrobotRehabPlan centrobotRehabPlan = r.getCentrobotRehabPlan();
			centrobotRehabPlan.setSubTrainingType(type);
			centrobotRehabPlanService.update(centrobotRehabPlan);
		}
	}

	/**
	 * ======================= 声明所有的service实例<都是单例>====================
	 * 需要在里面添加就行
	 */
	// 使用protected是的子类能够访问
	@Resource
	protected UserService userService;
	@Resource
	protected PatientService patientService;
	@Resource
	protected DoctorService doctorService;
	@Resource
	protected TherapistService therapistService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected RehabPlanService rehabPlanService;
	@Resource
	protected RehabExperienceService rehabExperienceService;
	@Resource
	protected EquipmentUsedService equipmentUsedService;
	@Resource
	protected MessageService messageService;
	@Resource
	protected CentrobotRehabPlanService centrobotRehabPlanService;
	@Resource 
	protected CentrobotDataService centrobotDataService;
}
