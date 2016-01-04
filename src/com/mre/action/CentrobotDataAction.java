package com.mre.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.base.BaseAction;
import com.mre.domain.CentrobotData;
import com.mre.domain.CentrobotRehabPlan;
import com.mre.domain.RehabPlan;
import com.mre.json.domin.AjaxRequireDataVO;
import com.mre.json.domin.CtrCommand;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CentrobotDataAction extends BaseAction<CentrobotData> {

	private static final long serialVersionUID = 1989289017746867750L;
//	private Long rehabPlanId;
//	private Float samplingTime;
	private String clientData; // 来自客户端的数据
	private Object serverData; // 来自服务端的数据 （回复或指令）
	
	/** ----------------- Device Client Data Processing ---------------- **/
	@SuppressWarnings("unchecked")
	public String browserCommandProcess() throws Exception {
		// 1. 解析clientData
		// 2. 判断command指令
		// 3. 根据command指令，做不同处理
		// 4. 将指令写入commandTable
		if (null != clientData) {
			// String --> jSONObject
			JSONObject receiveData = JSONObject.fromObject(clientData);
			String devUID = receiveData.getString("devUID");
			String command = receiveData.getString("command");
			System.out.println(command);
			Long rehabPlanId = Long.parseLong(receiveData.getString("data"));
			// System.out.println("Command = " + command);
			CtrCommand ctrCommand = new CtrCommand();
			ctrCommand.setDevUID(devUID);
			ctrCommand.setCommand(command);
			JSONObject data = null;
			if (command.equals("planContents")) {
				// 同步计划
				// 1. 将正在进行的centrobotRehabPlan写入application
				CentrobotRehabPlan rehabPlan = rehabPlanService.getById(
						rehabPlanId).getCentrobotRehabPlan();
				// System.out.println("Application has included the devUID_rehabPlan");
				ActionContext.getContext().getApplication().put(devUID + "_rehabPlan", rehabPlan);
				// 2. 准备data
				data = prepareData(rehabPlan);

			} else if (command.equals("toPosition")) {
				// 就位
				data = JSONObject.fromObject("{"
						+ "\"time\":6.0f," // 单位为 s
						+ "\"armAngle\":50.0f," + "\"shoulderAngle\":10.0f,"
						+ "\"elbowAngle\":20.0f" + "}");
			} else if (command.equals("toZero")) {
				// 零位
				data = JSONObject.fromObject("{\"time\":6.0f}");
			} else if (command.equals("upSpeed")) {
				// 加速
				data = JSONObject.fromObject("{" + "\"level\":\"small\"," + "\"value\":5" + "}");

			} else if (command.equals("downSpeed")) {
				// 减速
				data = JSONObject.fromObject("{" + "\"level\":\"small\"," + "\"value\":5" + "}");
			} else if (command.equals("upForce")) {
				// 增力
				data = JSONObject.fromObject("{" + "\"level\":\"small\"," + "\"value\":5" + "}");
			} else if (command.equals("downForce")) {
				// 减力
				data = JSONObject.fromObject("{" + "\"level\":\"small\"," + "\"value\":5" + "}");
			}
			ctrCommand.setData(data);
			// data无需值
			/*
			 * else if (command.equals("start")) { // 开始训练 } else if
			 * (command.equals("pause")) { // 暂停训练 } else if
			 * (command.equals("stop")) { // 停止训练 }
			 */

			// 3. 写入 commandTable
			Map<String, Object> appMap = ActionContext.getContext().getApplication();
			// 如果原先不存在，则创建commandTable
			if (!appMap.containsKey(devUID + "_command")) {
				System.out.println("commandTable is created!");
				List<CtrCommand> commandList = new ArrayList<CtrCommand>();
				commandList.add(ctrCommand);
				appMap.put(devUID + "_command", commandList);
			} else {
				// 存在则直接向其中添加即可指令
				System.out.println("one command is added!");
				((ArrayList<CtrCommand>) appMap.get(devUID + "_command"))
						.add(ctrCommand);
			}
		}
		return SUCCESS;
	}

	/** ------------------------- Ajax Data Processing ------------------- **/
	/**
	 * 1. ajax poll:浏览器通过ajax定期向服务器发出请求，服务器有新数据后 2. long poll :
	 * 长轮询，请求之后，直到有新的消息才返回 首先是 ajax轮询 ，ajax轮询
	 * 的原理非常简单，让浏览器隔个几秒就发送一次请求，询问服务器是否有新信息。 场景再现： 客户端：啦啦啦，有没有新信息(Request)
	 * 服务端：没有（Response） 客户端：啦啦啦，有没有新信息(Request) 服务端：没有。。（Response）
	 * 客户端：啦啦啦，有没有新信息(Request) 服务端：你好烦啊，没有啊。。（Response） 客户端：啦啦啦，有没有新消息（Request）
	 * 服务端：好啦好啦，有啦给你。（Response） 客户端：啦啦啦，有没有新消息（Request）
	 * 服务端：。。。。。没。。。。没。。。没有（Response） ---- loop
	 * 
	 * long poll long poll 其实原理跟 ajax轮询
	 * 差不多，都是采用轮询的方式，不过采取的是阻塞模型（一直打电话，没收到就不挂电话），
	 * 也就是说，客户端发起连接后，如果没消息，就一直不返回Response给客户端。直到有消息才返回，返回完之后，客户端再次建立连接，周而复始。
	 * 场景再现 客户端：啦啦啦，有没有新信息，没有的话就等有了才返回给我吧（Request） 服务端：额。。 等待到有消息的时候。。来
	 * 给你（Response） 客户端：啦啦啦，有没有新信息，没有的话就等有了才返回给我吧（Request） -loop
	 * 
	 * 从上面可以看出其实这两种方式，都是在不断地建立HTTP连接，然后等待服务端处理，可以体现HTTP协议的另外一个特点，被动性。
	 * 何为被动性呢，其实就是，服务端不能主动联系客户端，只能有客户端发起。
	 * 简单地说就是，服务器是一个很懒的冰箱（这是个梗）（不会、不能主动发起连接），但是上司有命令，如果有客户来，不管多么累都要好好接待。
	 * 
	 * 从上面很容易看出来，不管怎么样，上面这两种都是非常消耗资源的。 ajax轮询 需要服务器有很快的处理速度和资源。（速度） long poll
	 * 需要有很高的并发，也就是说同时接待客户的能力。（场地大小） 所以ajax轮询 和long poll 都有可能发生这种情况。 3. socket
	 * long connect 图表显示区，来获取最新的data
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxDataProcess() throws Exception {
		// 数据库里每500ms,会存入 10个数据
		if (null != clientData) {
			JSONObject receiveData = JSONObject.fromObject(clientData);
			Long rehabPlanId = receiveData.getLong("rehabPlanId");
			float samplingTime = (float) receiveData.getDouble("samplingTime");
			System.out.println("采样时间点：" + samplingTime);
			// !test! , 根据采样时间，每次取15个出来
			List<CentrobotData> dataList = centrobotDataService.get15DataBySamplingTimeAndRehabPlanId(samplingTime, rehabPlanId);
			if(dataList.size() != 0){
				List<AjaxRequireDataVO> reverseDataList = new ArrayList<AjaxRequireDataVO>();
				for(CentrobotData c:dataList){
					AjaxRequireDataVO a = new AjaxRequireDataVO(
						c.getArmSpeed(), 
						c.getArmDegree(), 
						c.getShoulderSpeed(), 
						c.getShoulderDegree(),
						c.getElbowSpeed(), 
						c.getElbowDegree(),
						c.getPositionX(), 
						c.getPositionY(),
						c.getPositionZ(), 
						c.getSamplingTime());
				reverseDataList.add(a);
			}
			
			// 1. 从数据库里拿最新的数据，一次拿15个数据，图表区横坐标设计成15个
			/*List<CentrobotData> dataList = centrobotDataService
					.get15DataByRehabPlanId(rehabPlanId);
			// 2. 判断是否为空，不为空则倒序排列，因为取出的时候是要从采样时间小的开始的
			if (dataList.size() != 0) {
				List<AjaxRequireDataVO> reverseDataList = new ArrayList<AjaxRequireDataVO>();
				CentrobotData c;
				for (CentrobotData c:dataList) {
					AjaxRequireDataVO a = new AjaxRequireDataVO(
							c.getArmSpeed(), 
							c.getArmDegree(), 
							c.getShoulderSpeed(), 
							c.getShoulderDegree(),
							c.getElbowSpeed(), 
							c.getElbowDegree(),
							c.getPositionX(), 
							c.getPositionY(),
							c.getPositionZ(), 
							c.getSamplingTime());
					reverseDataList.add(a);
					System.out.println(a);
				}*/
				
			/*	Map<String, List<CentrobotData>> dataMap = new HashMap<String, List<CentrobotData>>();
				dataMap.put("data", reverseDataList);*/
				
				// 注意list转化为json时，可以转化为数组，不需要使用map来自行构造		
				serverData = JSONArray.fromObject(reverseDataList);
			} else {
				serverData = null; 
				System.out.println("暂时没有数据！");
			}
				
		}
		/*
		 * if(null != clientData){ JSONObject receiveData =
		 * JSONObject.fromObject(clientData); String rehabPlanId =
		 * receiveData.get("rehabPlanId").toString(); severData =
		 * "{\"rehabPlanId\":\"" + rehabPlanId +"\"}"; //
		 * System.out.println(severData); }
		 */
		return SUCCESS;
	}
	
	/**
	 * 在训练完成之后，服务器要完成的数据保存和处理工作
	 * 接收到json数据为 {"rehabPlanId": , "startTime": , "endTime": }
	 * @return
	 * @throws Exception
	 */
	public String ajaxDataFinish() throws Exception{
		if(null != clientData){
			JSONObject receiveData = JSONObject.fromObject(clientData);
			// 1. 接收数据
			Long centrobotRehabPlanId = receiveData.getLong("rehabPlanId");
			Long startTime = receiveData.getLong("startTime");
			Long endTime = receiveData.getLong("endTime");
			int actualExerciseTime = (int)((endTime - startTime)/3600); // 单位分钟
			
			// 2. 处理数据
			// 2-1. centrobotRehabPlan -- starTime & endTime
			CentrobotRehabPlan centrobotRehabPlan = centrobotRehabPlanService.getById(centrobotRehabPlanId);
			centrobotRehabPlan.setStartTime(new Date(startTime));
			centrobotRehabPlan.setEndTime(new Date(endTime));
			centrobotRehabPlanService.update(centrobotRehabPlan);
			
			// 2-2. rehabPlan -- actualExerciseTime(min) / completed true / result = "良好"
			RehabPlan rehabPlan = centrobotRehabPlan.getRehabPlan();
			rehabPlan.setActualExerciseTime(actualExerciseTime);
			rehabPlan.setResult("良好");
			rehabPlan.setCompleted(true);
			rehabPlanService.update(rehabPlan);
			
			// 3. 清除application中相关的 commandList 和 rehabPlan
			String devUID = rehabPlan.getEquipment().getNumber();
			if(null != ActionContext.getContext().getApplication().get(devUID+"_command")){
				ActionContext.getContext().getApplication().remove(devUID+"_command");
			}
			if(null != ActionContext.getContext().getApplication().get(devUID+"_rehabPlan")){
				ActionContext.getContext().getApplication().remove(devUID+"_rehabPlan");
			}
			
			// 4. 删除centrobotData 只保留前100s的数据
			centrobotDataService.deleteAfter100(centrobotRehabPlanId);
		}
		return SUCCESS;
	}
	
	/**
	 * 停止训练后，删除原先接收到的数据
	 * @return
	 * @throws Exception
	 */
	public String AjaxDataClearData() throws Exception {
		if(null != clientData){
			JSONObject receiveData = JSONObject.fromObject(clientData);
			centrobotDataService.deleteByRehabPlanId(receiveData.getLong("rehabPlanId"));
		}
		return SUCCESS;
	}
	
	/**
	 * 显示报告时需要获取的数据
	 * @return
	 * @throws Exception
	 */
	public String ajaxDataReport() throws Exception {
		if(null != clientData){
			JSONObject receiveData = JSONObject.fromObject(clientData);
			Long rehabPlanId = receiveData.getLong("rehabPlanId");
			System.out.println(rehabPlanId);
			List<CentrobotData> modelList = centrobotDataService.getAllDataByCentrobotDataId(rehabPlanId);
			
			// JSONArray.fromObject(obj)方法在执行是会不断地对传入对象进行序列化，直到不可分解为止
			// 本例中 CentrobotData对象中有关联 centrobotRehabPlan对象，就会加载该对象，
			// 在加载该对象时发现有CentRobotData属性，就会选择加载，而此属性是懒加载的，所以序列化时会报出
			// net.sf.json.JSONException: org.hibernate.LazyInitializationException 的错误
			// 解决办法，可以过滤掉obj中不需要加载的属性
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[]{"centrobotRehabPlan", "equipmentUsed"}); // 传入需要过滤的属性
			serverData = JSONArray.fromObject(modelList, jc);   
		}
		return SUCCESS;
	}
	/** ========================= private method ========================= **/
	/**
	 * 根据centrobotRehabPlan构造data字符串
	 * 
	 * @param rehabPlan
	 * @return
	 * @throws Exception
	 */
	private JSONObject prepareData(CentrobotRehabPlan rehabPlan) throws Exception {
		// 训练类型
		String trainingType = rehabPlan.getTrainingType().startsWith("主动") ? "Active"
				: "Passive";
		// 训练子类型 ！想死的心都有，一开始数据库设计就没有按照这个格式来，肯定不好处理！
		String trainingSubType = "";
		String trainingForceRange = "";
		int trainingForce = 0;
		String forceOrientation = null;
		if (trainingType.equals("Active")) {
			trainingForceRange = rehabPlan.getForceRange().equals("大") ? "large"
					: (rehabPlan.getForceRange().equals("中") ? "medium"
							: "small");
			trainingForce = rehabPlan.getForceValue();
			// 助力的方向
			forceOrientation = "vertical"; // 默认是垂直方向
			if (rehabPlan.getSubTrainingType().startsWith("方向"))
				trainingSubType = "forceAssist";
			else if (rehabPlan.getSubTrainingType().startsWith("肌电"))
				trainingSubType = "EMGAssist";
			else if (rehabPlan.getSubTrainingType().startsWith("自由"))
				trainingSubType = "freedom";
			else if (rehabPlan.getSubTrainingType().startsWith("阻力"))
				trainingSubType = "resistance";
			else{
				throw new Exception("数据错误");
			}
		} else {
			trainingSubType = rehabPlan.getTrainingMotion().startsWith("预定义") ? "standard"
					: "userDefined";
		}
		// 动作编号
		int trainingNum = Integer.parseInt(rehabPlan.getTrainingMotion().split(
				"动作")[1]);
		int trainingTimes = rehabPlan.getTrainingCount();
		int trainingPeriod = rehabPlan.getTimePerTraining();
		System.out.println(JSONObject.fromObject("{" + "\"trainingType\":\"" + trainingType + "\","
				+ "\"trainingSubType\":\"" + trainingSubType + "\","
				+ "\"trainingNum\":" + trainingNum + "," + "\"trainingTimes\":"
				+ trainingTimes + "," + "\"trainingPeriod\":" + trainingPeriod
				+ "," + "\"trainingForceRange\":\"" + trainingForceRange
				+ "\"," + "\"trainingForce\":" + trainingForce + ","
				+ "\"forceOrientation\":\"" + forceOrientation + "\"" + "}"));
		return JSONObject.fromObject("{" + "\"trainingType\":\"" + trainingType + "\","
				+ "\"trainingSubType\":\"" + trainingSubType + "\","
				+ "\"trainingNum\":" + trainingNum + "," + "\"trainingTimes\":"
				+ trainingTimes + "," + "\"trainingPeriod\":" + trainingPeriod
				+ "," + "\"trainingForceRange\":\"" + trainingForceRange
				+ "\"," + "\"trainingForce\":" + trainingForce + ","
				+ "\"forceOrientation\":\"" + forceOrientation + "\"" + "}");
	}

	/* ========================= Getters And Setters =================== */

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

/*	public Long getRehabPlanId() {
		return rehabPlanId;
	}

	public void setRehabPlanId(Long rehabPlanId) {
		this.rehabPlanId = rehabPlanId;
	}

	public String getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(String samplingTime) {
		this.samplingTime = samplingTime;
	}*/
}
