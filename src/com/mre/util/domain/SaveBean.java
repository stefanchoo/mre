package com.mre.util.domain;



/**
 * 用来区分登录人的身份，在退出时要保存的量
 * @author Administrator
 *
 */
public class SaveBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4843535590574122751L;
	/* 在用户退出的时候保存 累计在线时间 */
	private Long loginTimestamp;                    // 登录时间戳
	private String onlineTime;                      // 累计在线时间
	private int level;                              // 用户等级
	private String discriminator;                   // 区分登录用户身份字段
	
	public Long getLoginTimestamp() {
		return loginTimestamp;
	}
	public void setLoginTimestamp(Long loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getDiscriminator() {
		return discriminator;
	}
	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}
	@Override
	public String toString() {
		return "SaveBean [loginTimestamp="
				+ loginTimestamp + ", onlineTime=" + onlineTime + ", level="
				+ level + ", discriminator=" + discriminator + "]";
	}
	
}
