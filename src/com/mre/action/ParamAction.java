package com.mre.action;

import java.util.Enumeration;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mre.json.domin.Data;
import com.mre.json.domin.DeviceInfo;
import com.mre.json.domin.Trainning;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ParamAction extends ActionSupport {
	
	JSONObject trainning = new JSONObject();
	JSONObject data = new JSONObject();
	/**
	 * 
	 */
	private static final long serialVersionUID = -2079821162599354989L;
	private String jsonString;
	
	public String receiveParams() throws Exception {
		if(jsonString != null){
			// 1. ip地址得保存起来
			ServletActionContext.getResponse().addHeader("keep-alive", "timeout=300, max=100");
			String ipAddr = ServletActionContext.getRequest().getRemoteAddr();
			String host = ServletActionContext.getRequest().getRemoteHost();
			int port = ServletActionContext.getRequest().getRemotePort();
			System.out.println("字头：");
			Enumeration<String> headers = ServletActionContext.getRequest().getHeaderNames();
			while(headers.hasMoreElements()){
				String s = headers.nextElement();
				System.out.println(s + ":" + ServletActionContext.getRequest().getHeader(s));
			}
			System.out.println("客户端IP地址：" + ipAddr);
			System.out.println("客户端主机：" + host);
			System.out.println("客户端端口：" + port);
			trainning = JSONObject.fromObject(jsonString);
			int method = trainning.getInt("servMethod");
			data = trainning.getJSONObject("data");
			String mac = data.getString("mac");
			String devUID = data.getString("devUID");
			
			Data d = new Data(mac, devUID, 1);
			Trainning t = new Trainning(method, d);
			DeviceInfo dev = new DeviceInfo(devUID, mac, ipAddr);
			
			System.out.println("-------解析数据开始--------");
			System.out.println("servMethod = " + method);
			System.out.println("data：{");
			System.out.println("mac = "+ mac);
			System.out.println("decUID = " + devUID);
			System.out.println("}");
			System.out.println("-------数据解析完成--------");	
			// 存储设备号和ip地址
			ActionContext.getContext().getApplication().put(devUID, dev);
		return "toStateAction_success";
		}
		return "toStateAction_fail";
	}
	
	public String sendCommand() throws Exception {
		// 重新建立连接
		
		return "toCommandAction_command";
	}
	
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
