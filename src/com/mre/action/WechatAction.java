package com.mre.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.domain.Doctor;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.Message;
import com.mre.domain.Patient;
import com.mre.domain.RehabPlan;
import com.mre.domain.Therapist;
import com.mre.service.DoctorService;
import com.mre.service.EquipmentUsedService;
import com.mre.service.MessageService;
import com.mre.service.PatientService;
import com.mre.service.RehabPlanService;
import com.mre.service.TherapistService;
import com.mre.service.UserService;
import com.mre.util.Formatter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 处理与微信端数据交互的action
 * 
 * @author Administrator
 * 
 */

@Controller
@Scope("prototype")
public class WechatAction extends ActionSupport {
	private static final long serialVersionUID = 8603452318236892426L;
	// 接收传递过来的json数据
	private String jsonString;
	// 返回数据
	private Map<String, Object> returnData = new LinkedHashMap<String, Object>();

	@Resource
	private UserService userService;
	@Resource
	private DoctorService doctorService;
	@Resource
	private PatientService patientService;
	@Resource
	private TherapistService therapistService;
	@Resource
	private EquipmentUsedService equipmentService;
	@Resource
	private MessageService messageService;
	@Resource
	private RehabPlanService rehabPlanService;

	/**
	 * 返回绑定的结果
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bindAccount() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			// 1. 验证用户是否存在
			// 2. 若存在将opendid写入数据库进行绑定
			// 3. 返回数据 openid, bind_result, identity.
			returnData = userService.bindAccountAndGetIdentity(
					receiveData.get("username").toString(),
					receiveData.get("password").toString(),
					receiveData.get("openid").toString());
		}
		return SUCCESS;
	}

	/**
	 * 获取个人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPersonalInfo() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			// 1. 获取openid，根据openid, 来查询消息
			returnData = userService.getByOpenid(receiveData.get("openid")
					.toString());
		}
		return SUCCESS;
	}

	/**
	 * 获取患者列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPatientsList() throws Exception {
		if (null != jsonString) {
			// 1. 获取openid
			// 2. 根据openid, 来查询患者列表
			// 3. 构造返回数据的格式
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			List<Patient> patientsList = patientService
					.findAllByDoctorOpenid(receiveData.get("openid").toString());
			if (patientsList.size() != 0) {
				int count = 0;
				for (Patient patient : patientsList) {
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("姓名", patient.getTrueName());
					dataMap.put("性别", patient.getGender());
					dataMap.put("微信", patient.getWeixin());
					returnData.put(count + "", dataMap);
					count++;
				}
			} else {
				returnData.put("errorMsg", "很抱歉，您还没有关联过患者！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取医生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDoctorInfo() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			returnData = patientService.getPatientByOpenid(//
					receiveData.get("openid").toString());
		}
		return SUCCESS;
	}

	/**
	 * 获取设备列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getEquipsList() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			List<EquipmentUsed> euipsList = equipmentService
					.getByTherapistOpenid(//
					receiveData.get("openid").toString());
			if (euipsList.size() != 0) {
				int count = 0;
				for (EquipmentUsed euip : euipsList) {
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("名称", euip.getName());
					returnData.put(count + "", dataMap);
					count++;
				}
			} else {
				returnData.put("errorMsg", "很抱歉，没有找到设备！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取新消息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getNewMsg() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			List<Message> newMsgsList = messageService.getByToUserOpenid(//
					receiveData.get("openid").toString(), false, false);
			if (newMsgsList.size() != 0) {
				int count = 0;
				for (Message msg : newMsgsList) {
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("发件人", msg.getFromUser().getTrueName());
					dataMap.put("主题", msg.getTitle());
					dataMap.put("时间", Formatter.formatWithPattern(
							msg.getPostTime(), "yyyy-MM-dd HH:mm:ss"));
					returnData.put(count + "", dataMap);
					count++;
				}
			} else {
				returnData.put("errorMsg", "暂时没有新信息！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取今日计划
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTodayPlan() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			List<RehabPlan> rehabsList = rehabPlanService
					.getTodayRehabPlanListByOpenid(
							//
							receiveData.get("openid").toString(), receiveData
									.getString("identity").toString());
			if (rehabsList.size() != 0) {
				int count = 0;
				for (RehabPlan rehabPlan : rehabsList) {
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("时间", Formatter.formatWithPattern(
							rehabPlan.getTime(), "HH:mm"));
					dataMap.put("训练设备", rehabPlan.getEquipment().getName());
					dataMap.put("训练时间", rehabPlan.getExerciseTime() + "分钟");
					returnData.put(count + "", dataMap);
					count++;
				}
			} else {
				returnData.put("errorMsg", "您今天没有训练计划！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 医生获取同一医院部门的治疗师消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTherapistsList() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			Doctor d = doctorService.findByOpenid(receiveData.get("openid")
					.toString());
			if (null != d && null != d.getHospital()) {
				Long hospitalId = d.getHospital().getId();
				List<Therapist> therapiststList = therapistService
						.findAllByHospitalId(hospitalId);
				if (therapiststList.size() != 0) {
					int count = 0;
					for (Therapist therapist : therapiststList) {
						Map<String, String> dataMap = new LinkedHashMap<String, String>();
						dataMap.put("姓名", therapist.getTrueName());
						dataMap.put("性别", therapist.getGender());
						dataMap.put("微信", therapist.getWeixin());
						returnData.put(count + "", dataMap);
						count++;
					}
				} else {
					returnData.put("errorMsg", "很抱歉，未查询到您需要的信息！");
				}
			} else {
				returnData.put("errorMsg", "很抱歉，未查询到您需要的信息！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据患者的姓名查找患者信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPatientInfo() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			Patient p = patientService.getByNameAndDoctorOpenid(receiveData
					.get("patient_name").toString(), receiveData.get("openid")
					.toString());
			if (null != p) {
				returnData.put("姓名", p.getTrueName());
				returnData.put("性别", p.getGender());
				returnData.put("微信", p.getWeixin());
				returnData.put("疾患", p.getIllness());
				returnData.put("入院时间", Formatter.formatWithPattern(
						p.getBeAdmissionTime(), "yyyy-MM-dd"));
			} else {
				returnData.put("errorMsg", "未查询到患者信息");
			}
		}
		return SUCCESS;
	}

	/**
	 * 通过患者姓名获取其训练计划
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRehabPlansListByPatient() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			Patient p = null;
			if (receiveData.get("identity").toString().equals("doctor")) {
				p = patientService.getByNameAndDoctorOpenid(receiveData
						.get("patient_name").toString(),
						receiveData.get("openid").toString());
			} else if (receiveData.get("identity").toString().equals("patient")) {
				p = patientService.getByOpenid(receiveData.get("openid").toString());
			}
			if (null != p) {
				List<RehabPlan> rehabPlansList = rehabPlanService
						.get7ByPatientId(p.getId());
				if (rehabPlansList.size() == 0) {
					returnData.put("errorMsg", "该患者还没有训练计划！");
				}
				int count = 0;
				for (RehabPlan rehabPlan : rehabPlansList) {
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("时间", Formatter.formatWithPattern(
							rehabPlan.getTime(), "MM-dd HH:mm"));
					dataMap.put("训练类型", rehabPlan.getType());
					dataMap.put("训练设备", rehabPlan.getEquipment().getName());
					dataMap.put("治疗师", rehabPlan.getEquipment().getTherapist()
							.getTrueName());
					dataMap.put("完成情况", rehabPlan.isCompleted() ? "是" : "否");
					returnData.put(count + "", dataMap);
					count++;
				}
			} else {
				returnData.put("errorMsg", "该患者不存在！");
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据治疗师的openid, 查询医生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDoctorsList() throws Exception {
		if (null != jsonString) {
			JSONObject receiveData = JSONObject.fromObject(jsonString);
			Therapist t = therapistService.findByOpenid(receiveData.get(
					"openid").toString());
			if (null != t && null != t.getHospital()) {
				Long hospitalId = t.getHospital().getId();
				List<Doctor> doctorsList = doctorService
						.findAllByHospitalId(hospitalId);
				if (doctorsList.size() != 0) {
					int count = 0;
					for (Doctor d : doctorsList) {
						Map<String, String> dataMap = new LinkedHashMap<String, String>();
						dataMap.put("姓名", d.getTrueName());
						dataMap.put("性别", d.getGender());
						dataMap.put("微信", d.getWeixin());
						returnData.put(count + "", dataMap);
						count++;
					}
				} else {
					returnData.put("errorMsg", "很抱歉，未查询到您需要的信息！");
				}
			} else {
				returnData.put("errorMsg", "很抱歉，未查询到您需要的信息！");
			}
		}
		return SUCCESS;
	}

	/** -------------------- Getter And Setter -------------------------- **/

	public Map<String, Object> getReturnData() {
		return returnData;
	}

	public void setReturnData(Map<String, Object> returnData) {
		this.returnData = returnData;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
