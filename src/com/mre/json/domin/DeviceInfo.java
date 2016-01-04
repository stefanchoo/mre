package com.mre.json.domin;

public class DeviceInfo {
	private String devUID;
	private String mac;
	private String addr;
	
	public DeviceInfo() {
		super();
	}

	public DeviceInfo(String devUID, String mac, String addr) {
		super();
		this.devUID = devUID;
		this.mac = mac;
		this.addr = addr;
	}
	
	public String getDevUID() {
		return devUID;
	}
	public void setDevUID(String devUID) {
		this.devUID = devUID;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "DeviceInfo [devUID=" + devUID + ", mac=" + mac + ", addr="
				+ addr + "]";
	}
}
