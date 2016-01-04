package com.mre.json.domin;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 指令表对象
 * @author Administrator
 *
 */
public class CtrCommand implements Serializable{
	private static final long serialVersionUID = -1174272272222057829L;
	private String devUID;       // => key
	private String command;
	private JSONObject data;
	
	public String getDevUID() {
		return devUID;
	}
	public void setDevUID(String devUID) {
		this.devUID = devUID;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CtrCommand [devUID=" + devUID + ", command=" + command
				+ ", data=" + data + "]";
	}
}
