package com.mre.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.CentrobotRehabPlan;
import com.mre.domain.Doctor;
import com.mre.domain.Patient;
import com.mre.domain.RehabPlan;
import com.mre.domain.Therapist;
import com.mre.util.domain.SaveBean;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CentrobotAction extends BaseAction<CentrobotRehabPlan> {

	private static final long serialVersionUID = 9028655949397075382L;

	private String opr; // 用来区别是怎么样的操作
	private Long rehabPlanId;
	private Long patientId;
	private Long equipmentId;
	private String patientName;
	private String target; // iframe 该显示的地方

	/** ---------------- 页面显示部分 -------------- **/
	/**
	 * 转到首页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		getMessagesCount();
		ActionContext.getContext().put("equipmentId", equipmentId);
		return "index";
	}

	/**
	 * 患者列表页面
	 */
	public String patientsList() throws Exception {
		getMessagesCount();
		if (opr.equals("docOpr") || opr.equals("theOpr")) {
			// 2. 医生查看设备直接进入的情况
			// 这时左侧患者列表显示该医生使用该设备训练的所有患者，右侧计划显示第一个患者的第一个待完成的计划
			// 把第一个病人的id传递给cent_planContent
			ActionContext.getContext().put("patientId", getPatients( //
					getSaveBean(), getCurrentUser()).get(0).getId());
		} else if (opr.equals("searchPatient")) {
			// 搜索用户
			if (null != searchPatientResult()) {
				patientId = searchPatientResult().getId();
				ActionContext.getContext().put("searchPatientId", patientId);
			}
		}
		ActionContext.getContext().put("equipmentId", equipmentId);
		// 把target 传递进来在链接时用得到
		ActionContext.getContext().put("target", "patientReport");
		return "patientsList";
	}

	/**
	 * 计划定制页面 
	 * 1. 接收的参数为传递过来的rehabPlanId 
	 * 2. 医生或治疗师查看设备过来的页面 
	 * 3. 搜索患者的时候，提交的页面
	 * @return
	 * @throws Exception
	 */
	public String makePlansUI() throws Exception {
		getMessagesCount();
		if (opr.equals("fromDoc")) {
			// 1. 医生从 计划定制 页面 转过来的操作
			// 传递过来的参数为 rehabPlanId 以及 opr = fromDoc (实际上 saveBean
			// 中"discriminator"也可以作此判断)
			RehabPlan rehabPlan = rehabPlanService.getById(rehabPlanId);
			model = rehabPlan.getCentrobotRehabPlan();
			ActionContext.getContext().put("model", model);
			equipmentId = rehabPlan.getEquipment().getId();

		} else if (opr.equals("docOpr") || opr.equals("theOpr")) {
			// 2. 医生查看设备直接进入的情况
			// 这时左侧患者列表显示该医生使用该设备训练的所有患者，右侧计划显示第一个患者的第一个待完成的计划
			// 把第一个病人的id传递给cent_planCotent
			ActionContext.getContext().put("patientId", getPatients(//
					getSaveBean(), getCurrentUser()).get(0).getId());

		} else if (opr.equals("searchPatient")) {
			// 搜索用户
			if (null != searchPatientResult()) {
				patientId = searchPatientResult().getId();
				ActionContext.getContext().put("searchPatientId", patientId);
			}
		}
		ActionContext.getContext().put("equipmentId", equipmentId);
		// 把target 传递进来在链接时用得到
		ActionContext.getContext().put("target", "planContent");
		return "makePlansUI";
	}

	/**
	 * 康复训练页面 这个页面其中的graph iframe，5张图表，要定时刷新，目前可以设置为1s自动刷新一次 ，每次更新15个点 页面上包含：
	 *  1. graph iframe，5张图表 2s书刷新一次 
	 *  2. 按钮 同步计划 就位 开始训练 暂停训练 停止训练 加速 减速 增力 减力 归零 生成计划
	 * @return
	 * @throws Exception
	 */
	public String rehabTrainingUI() throws Exception {
		getMessagesCount();
		ActionContext.getContext().put("equipmentId", equipmentId);
		// 取出第一个计划的内容，传递给页面
		List<RehabPlan> rehabPlansList = rehabPlanService
				.getTodayRehabPlansByEquipment(equipmentId);
		if(rehabPlansList.size() != 0)
			rehabPlanId = rehabPlansList.get(0).getId();
		ActionContext.getContext().put("rehabPlanId", rehabPlanId);
		return "rehabTrainingUI";
	}

	/**
	 * 设备调试页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String equipmentCheckUI() throws Exception {
		getMessagesCount();
		ActionContext.getContext().put("equipmentId", equipmentId);
		return "equipmentCheckUI";
	}

	/**
	 * 帮助页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String helpPage() throws Exception {
		getMessagesCount();
		ActionContext.getContext().put("equipmentId", equipmentId);
		return "helpPage";
	}

	/** --------------------iframe 中的页面---------------- **/

	/**
	 * iframe 中的病人列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cent_patientsList() throws Exception {
		/*
		 * 适用于两种情况： 1. 搜索时显示一个用户 2. 从医生的计划列表过来时
		 */
		if (null != patientId) {
			Patient patient = patientService.getById(patientId);
			/*
			 * // 得到年龄 ActionContext.getContext().put("age", patient.age() +
			 * "");
			 */
			ActionContext.getContext().put("patient", patient);
		} else {
			// 这时左侧患者列表显示该医生使用该设备训练的所有患者
			List<Patient> patientsList = getPatients(getSaveBean(),
					getCurrentUser());
			ActionContext.getContext().put("patientsList", patientsList);
		}
		// 将链接的target 传递过来
		ActionContext.getContext().put("target", target);
		return "cent_patientsList";
	}

	/**
	 * 患者计划详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cent_planContent() throws Exception {
		if (null != patientId) {
			// 患者最近7条未完成的计划，不足七条时有多少显示多少，这样就不用分页
			List<CentrobotRehabPlan> modelList = centrobotRehabPlanService
					.getLast7PlansByPatientId(patientId);
			ActionContext.getContext().put("modelList", modelList);
			if (modelList.isEmpty()) {
				return "noRehabPlanResult";
			}
			if (null != model.getId()) {
				model = centrobotRehabPlanService.getById(model.getId());
			} else {
				model = modelList.get(0);
			}

			ActionContext.getContext().put("model", model);
			return "cent_planContent";
		} else {
			return "noResult";
		}

	}

	/**
	 * 患者训练报告页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cent_patientReport() throws Exception {
		if (null != patientId) {
			// 患者完成的训练
			List<CentrobotRehabPlan> modelList = centrobotRehabPlanService
					.getCompletedPlansByPatientId(patientId);
			ActionContext.getContext().put("modelList", modelList);
			if (modelList.isEmpty()) {
				return "noReportResult";
			}
			if (null != model.getId()) {
				model = centrobotRehabPlanService.getById(model.getId());
			} else {
				model = modelList.get(0);
			}
			ActionContext.getContext().put("model", model);
			return "cent_patientReport";
		} else {
			return "noResult";
		}

	}
	
	/**
	 * 设备控制区的页面 
	 * @return
	 * @throws Exception
	 */
	public String cent_commandArea() throws Exception {
		// 这里构造一个 devUID给Ajax
//		String devUID = DigestUtils.md5Hex(equipmentUsedService.getById(equipmentId).getNumber()).toString();
		String devUID = equipmentUsedService.getById(equipmentId).getNumber();
		ActionContext.getContext().put("devUID", devUID);
		if(null != rehabPlanId){
			RehabPlan rehabPlan = rehabPlanService.getById(rehabPlanId);
			ActionContext.getContext().put("rehabPlan", rehabPlan);
		}
		ActionContext.getContext().put("equipmentId", equipmentId);
		return "cent_commandArea";
	}
	
	/**
	 * 用来在康复训练页面显示训练计划列表（！当天！） 取当天的该centrobot机器的训练计划表，
	 * 从中抽象出患者的姓名 时间 类型等 格式 姓名
	 * --- 时间 --- 类型（子类型）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cent_patientsRehabList() throws Exception {
		
		// 这里面centrobotRehabPlan
		List<RehabPlan> rehabPlansList = rehabPlanService
				.getTodayRehabPlansByEquipment(equipmentId);
		ActionContext.getContext().put("rehabPlansList", rehabPlansList);
		ActionContext.getContext().put("equipmentId", equipmentId);
		return "cent_patientsRehabList";
	}

	/** ----------------- 逻辑处理部分 -------------------- **/
	/**
	 * 处理计划更新
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateRehabPlan() throws Exception {
		// 取出model对象对应的rehabPlan
		CentrobotRehabPlan updateModel = centrobotRehabPlanService
				.getById(model.getId());
		/*System.out.println("我被更新了：" + model.getId() + " | "
				+ model.getTrainingType() + " | "
				+ model.getSubTrainingType());*/
		// 进行关联
		model.setRehabPlan(updateModel.getRehabPlan());
		// 更新训练计划
		centrobotRehabPlanService.update(model); 
		return "toRehabPlanContent";
	}
	
	/**
	 * 处理数据，显示图表
	 * @return
	 * @throws Exception
	 */
	public String graphShow() throws Exception {
		// 这里要不断地拿数据！！！，前台使用ajax 2s请求一次从后台取数据，封装好交给绘图软件进行绘图
		// 这里的rehabPlan 为 centrobotRrhabPlanId
		ActionContext.getContext().put("rehabPlanId", rehabPlanId);
		return "graphShow";
	}
	
	/** -------------------- Setter & Getter ------------------ **/
	public Long getRehabPlanId() {
		return rehabPlanId;
	}

	public void setRehabPlanId(Long rehabPlanId) {
		this.rehabPlanId = rehabPlanId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	/** ------------------ 其他抽取的方法 --------------------- **/
	/**
	 * 抽取该医生和使用该设备的患者列表
	 * @param id
	 * @return
	 */
	private List<Patient> getPatients(SaveBean saveBean, Object user) {
		List<CentrobotRehabPlan> centrobotRehabPlansList = new ArrayList<CentrobotRehabPlan>();
		if (saveBean.getDiscriminator().equals("doctor")) {
			centrobotRehabPlansList = centrobotRehabPlanService
					.getAllByDoctorId(((Doctor) getCurrentUser()).getId());
		} else if (saveBean.getDiscriminator().equals("therapist")) {
			centrobotRehabPlansList = centrobotRehabPlanService
					.getAllByTherapistId(((Therapist) getCurrentUser()).getId());
		}
		List<Patient> patients = new ArrayList<Patient>();
		for (CentrobotRehabPlan c : centrobotRehabPlansList) {
			Patient patient = new Patient();
			patient = c.getRehabPlan().getPatient();
			// 如果不是相同的patient则加入到list中
			if (!patients.contains(patient)) {
				patients.add(patient);
			}
		}
		return patients;
	}

	/**
	 * 查询的结果
	 */
	private Patient searchPatientResult() {
		List<Patient> patients = new ArrayList<Patient>();
		patients = getPatients(getSaveBean(), getCurrentUser());
		for (Patient patient : patients) {
			if (patient.getTrueName().equals(patientName)) {
				System.out.println("搜索到用户！");
				return patient;
			}
		}
		System.out.println("未找到用户！");
		return null;
	}
}
