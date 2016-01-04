package com.mre.action;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class StateAction extends ActionSupport {

	private static final long serialVersionUID = 5938688303941909591L;
	private JSONObject data = new JSONObject();
	
	public String success() throws Exception {
		data.put("success", true);
		return SUCCESS;
	}
	
	public String fail() throws Exception {
		data.put("success", false);
		return "fail";
	}
	
	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
	
}
