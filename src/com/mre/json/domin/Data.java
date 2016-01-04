package com.mre.json.domin;

public class Data implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4663744975248658456L;
	
	private String mac;
	private String devUID;
	private int status;
	
	
	public Data() {
		super();
	}
	
	public Data(String mac, String devUID, int status) {
		super();
		this.mac = mac;
		this.devUID = devUID;
		this.status = status;
	}

	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getDevUID() {
		return devUID;
	}
	public void setDevUID(String devUID) {
		this.devUID = devUID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Data [mac=" + mac + ", devUID=" + devUID + ", status=" + status
				+ "]";
	}
}
