package com.mre.action;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.domain.CentrobotData;
import com.mre.domain.CentrobotRehabPlan;
import com.mre.json.domin.CentrobotDataVO;
import com.mre.json.domin.CtrCommand;
import com.mre.service.CentrobotDataService;
import com.mre.service.RehabPlanService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 与设备通信的接口 数据格式： json
 * 这个是不需要经过 Privilege Interceptor的
 * @author Administrator
 * 
 */ 
@Controller
@Scope("prototype")
public class DeviceAction extends ActionSupport {
	private static final long serialVersionUID = -6973797347579243786L;
	private String clientData; // 来自客户端的数据
	private Object serverData; // 来自服务端的数据 （回复或指令）
	
	@Resource
	private  CentrobotDataService centrobotDataService;
	@Resource
	private RehabPlanService rehabPlanService;
	
	/*---------------------- Device Client Data Processing ---------------------*/
	/**
	 * 处理在线请求轮询指令 设备向服务器上传机器状态码
	 * @return
	 * @throws Exception
	 */
	public String keepOnline() throws Exception {
		if (null != clientData) {
			// String --> jSONObject
			JSONObject receiveData = JSONObject.fromObject(clientData);
			// 拿出command,判断是否是online,不是则返回{"status": "command error"}
			if (!receiveData.getString("command").equals("online")) {
				// 构造string 发送给客户端时会由 struts自动转换为 json数据格式
				// serverData = new String("{\"status\":\"command error\"}");
				serverData = JSONObject.fromObject("{\"status\":\"command error\"}");
				// severData = new HashMap<String,
				// String>().put("status","command error");
				return SUCCESS;
			}
			// 拿出 devUID， 查询Application中的指令表，是否有该设备的新指令，有则取出，没有则返回
			String devUID = receiveData.getString("devUID");
			System.out.println("机器的UID为： " + devUID);
			checkCommandTable(devUID);
			int statusCode = receiveData.getJSONObject("data").getInt(
					"statusCode");
			System.out.println("机器的状态码为： " + statusCode);
		}
		return SUCCESS;
	}

	/**
	 * 用来处理客户端上传的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateData() throws Exception {
		if (null != clientData) {
			// String --> jSONObject
			JSONObject receiveData = JSONObject.fromObject(clientData);
			if (!receiveData.getString("command").equals("updateData")) {
				// serverData = new String("{\"status\":\"command error\"}");
				serverData = JSONObject
						.fromObject("{\"status\":\"command error\"}");
				return SUCCESS;
			}
			if (null == receiveData.getJSONObject("data")) {
				// serverData = new String("{\"status\":\"data lost error\"}");
				serverData = JSONObject
						.fromObject("{\"status\":\"data lost error\"}");
				return SUCCESS;
			}
			// 拿出 devUID， 查询Application中的指令表，是否有该设备的新指令，有则取出，没有则返回
			// Appliation 中存储格式为 devUID_command -> CtrCommand
			String devUID = receiveData.getString("devUID");
			// check ApplicationContext command table
			checkCommandTable(devUID);
			// 根据devUID 查询 Application中的正在执行的计划表
			CentrobotRehabPlan rehabPlan = checkRehabPlanTable(devUID);
			// 处理数据
			JSONObject data = receiveData.getJSONObject("data");
			// 封装成VO对象
			CentrobotDataVO centrobotDataVO = new CentrobotDataVO(
					(float) data.getDouble("armVelocity"),
					(float) data.getDouble("shoulderVelocity"),
					(float) data.getDouble("elbowVelocity"),
					(float) data.getDouble("armAngle"),
					(float) data.getDouble("shoulderAngle"),
					(float) data.getDouble("elbowAngle"),
					(float) data.getDouble("samplingTime"));

			// 封装成CentrobotData对象
			CentrobotData model = new CentrobotData(
					centrobotDataVO.getArmVelocity(),
					centrobotDataVO.getArmAngle(),
					centrobotDataVO.getShoulderVelocity(),
					centrobotDataVO.getShoulderAngle(),
					centrobotDataVO.getElbowVelocity(),
					centrobotDataVO.getElbowAngle(),
					centrobotDataVO.getSamplingTime(), rehabPlan);
			model.setDevUID(devUID);
			model.setEquipmentUsed(rehabPlan.getRehabPlan().getEquipment());
			System.out.println(model.toString());
			// 存入数据库
			centrobotDataService.save(model);
			System.out.println("数据存储成功！");
		}
		return SUCCESS;
	}

	/**
	 * 设备的反馈，告知是否执行完指令
	 * 
	 * @return
	 * @throws Exception
	 */
	public String echo() throws Exception {
		if (null != clientData) {

			// String --> jSONObject
			JSONObject receiveData = JSONObject.fromObject(clientData);
			if (!receiveData.getString("command").equals("echo")) {
				// serverData = new String("{\"status\":\"command error\"}");
				serverData = JSONObject
						.fromObject("{\"status\":\"command error\"}");
				return SUCCESS;
			}

			// 判断反馈的控制指令的执行结果
			if (Boolean.parseBoolean(receiveData.getJSONObject("data")
					.getString("finished"))) {
				String devUID = receiveData.getString("devUID");
				checkCommandTable(devUID);
			} else {
				// serverData = new
				// String("{\"status\":\"I will wait for you\"}");
				serverData = JSONObject
						.fromObject("{\"status\":\"I will wait for you\"}");
			}
		}
		return SUCCESS;
	}
	
	/****************************** private method *****************************/
	/**
	 * 读取Application中的map
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getAppMap() throws Exception {
		return ActionContext.getContext().getApplication();
	}
	/**
	 * 根据设备编号，轮询指令表
	 * 
	 * @param devUID
	 *           :设备编号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void checkCommandTable(String devUID) throws Exception {
		// 在Application中记录一个时间戳，然后每次访问时写入新的时间戳，当两次时间戳时间大于5s时，
		// 认为已经断开了连接,需要清空指令表
		if(!getAppMap().containsKey(devUID+"_commandTime")){
			getAppMap().put(devUID+"_commandTime", System.currentTimeMillis());
		}else{
			long lastTime = (Long) getAppMap().get(devUID+"_commandTime");
			long now = System.currentTimeMillis();
			if( now - lastTime > 5000){
				getAppMap().remove(devUID + "_command");
			}
			getAppMap().put(devUID+"_commandTime", now);
		}
		//
		// Appliation 中存储格式为 devUID_command -> ArrayList<CtrCommand>
		// devUID_rehabPlan -> CentrobotRehabPlan

		// check ApplicationContext command table
		if (getAppMap().containsKey(devUID + "_command")) {
			System.out.println("有指令要发出！");
			ArrayList<CtrCommand> commandsList = (ArrayList<CtrCommand>) getAppMap()
					.get(devUID + "_command");
			// ArrayList中是否有多余一条的指令
			// 如有，则取出，然后将第一条记录清空，如果只有一条记录,取出后则删除key devUID_command
			// 没有remove掉key devUID_command
			if (commandsList.size() > 0) {
				CtrCommand ctrCommand = commandsList.get(0);
				if (commandsList.size() == 1) {
					getAppMap().remove(devUID + "_command");
				} else {
					commandsList.remove(0);
				}
				// 将ctrCommand 赋值给 severData
				serverData = JSONObject.fromObject(ctrCommand);
			}
		} else {
			System.out.println("指令表中没有指令");
//			serverData = new String("{\"status\":\"ok\"}");
			serverData = JSONObject.fromObject("{\"status\":\"ok\"}");
			//System.out.println(serverData);
		}
	}

	/**
	 * 根据devUID获取centRobotRehabPlan
	 * 
	 * @param devUID
	 * @return
	 * @throws Exception
	 */
	private CentrobotRehabPlan checkRehabPlanTable(String devUID)
			throws Exception {
		// Appliation 中存储格式为 devUID_command -> ArrayList<CtrCommand>
		// devUID_rehabPlan -> CentrobotRehabPlan
		CentrobotRehabPlan rehabPlan = null;
		// check ApplicationContext rehabPlan table
		if (getAppMap().containsKey(devUID + "_rehabPlan")) {
			System.out.println("获取到rehabPlan");
			rehabPlan = (CentrobotRehabPlan) getAppMap().get(
					devUID + "_rehabPlan");
		}else{
			System.out.println("未获取到rehabPlan");
		}
		return rehabPlan;
	}
	
	
	
	/****************** Getter & Setter *******************/
	public String getClientData() {
		return clientData;
	}

	public void setClientData(String clientData) {
		this.clientData = clientData;
	}

	public Object getServerData() {
		return serverData;
	}

	public void setServerData(Object serverData) {
		this.serverData = serverData;
	}
}
