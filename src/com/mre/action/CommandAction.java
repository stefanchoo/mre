package com.mre.action;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class CommandAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6224964105173982993L;
	private JSONObject command = new JSONObject();
	
	@Override
	public String execute() throws Exception {
		command.put("servMethod", 1);
		return SUCCESS;
	}

	public JSONObject getCommand() {
		return command;
	}

	public void setCommand(JSONObject command) {
		this.command = command;
	}
}
