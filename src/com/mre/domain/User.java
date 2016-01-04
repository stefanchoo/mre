package com.mre.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Type;

import com.mre.util.Formatter;

@Entity
//继承映射，指定表的生成策略为一张表
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator", discriminatorType=DiscriminatorType.STRING)

@DiscriminatorValue("user")
@Table(name="_user")
public class User implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1667512690375119953L;
	
	private Long id;
	private String loginName;                   								// 登录名
	private String password = DigestUtils.md5Hex("123456");                     // 登录密码、医生添加患者时 管理员添加医生时的初始密码
	private String trueName;                    								// 真实姓名
	private String gender = "保密";              									// 允许有保密项，所以不使用boolean，默认保密
	private Date birthday;                    								    // 出生年月
	private String icon;             											// 头像，存的是地址
	private String email;														// 邮箱地址
	private String telephone;                   								// 手机号码
	private String weixin;                      								// 微信
	private String openId;                                                      // 用户微信的身份标识
	private Hospital hospital;													// 医院
	private String city;                        								// 城市
	private java.util.Date registerTime;       									// 注册时间
	private java.util.Date lastLoginTime;                 						// 上次登录时间
	private int onlineTime;       								                // 累计在线时间
	private String ipAddress;        											// 登录的ip地址
	private int level;               											// 级别   20个小时为一级
	private Long credits = 20L;            										// 积分
	private int informWay;                      								// 短信通知的方式 默认为0 表示不使用此服务  1 手机  2 微信 3 手机+微信
	// 多对多的关系                      有多重身份的可能性，比如即是患者又是版主
	private Set<Role> roles;
	// 一对多关系
	private Set<Message> receivedMessages = new HashSet<Message>();
	// 一对多关系
	private Set<Message> sentMessages = new HashSet<Message>();
	// 一对多关系
	private Set<Topic> topics = new HashSet<Topic>();
	// 一对多关系
	private Set<Reply> replies = new HashSet<Reply>();
	//一对多关系
	private Set<RehabAssessment> rehabAssessmentsMade = new HashSet<RehabAssessment>();
	
	
	@Transient
	public boolean checkPrivilegeByUrl(String url){
		String urlActionName = "";
		if(url.contains("?")){
		// 去参数
			urlActionName = url.split("?")[0];
		}else{
		    urlActionName = url;
		}
		// 取Action的部分
		String urlStartWith = urlActionName.split("_")[0]+"_";
		List<String> priUrls = new ArrayList<String>();
		
		// 遍历出所有角色的权限数据
		for(Role role: this.getRoles()){
			for(Privilege p : role.getPrivileges()){
				priUrls.add(p.getUrl());
			}
		}
		if(priUrls.contains(urlStartWith) || priUrls.contains(urlActionName)){
			return true;
		}
		// 判断是否有此权限
		
		return false;
	}
	
	/**
	 * 根据生日获取年龄
	 * @return
	 */
	@Transient
	public int age(){
		return Formatter.getAgeByBirthday(this.getBirthday());
		
	}
	
	/**
	 * 获取身份信息
	 * @return
	 */
	@Transient
	public String identification() {
		return "管理员 -- " + this.getTrueName();
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length=30)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Column(length=32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(length=30)
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column(length=10)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Column(length=50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=60)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(length=30)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=20)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@ManyToOne
	@JoinColumn(name="hospitalId")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Type(type="timestamp")
	public java.util.Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}
	
	public int getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}
	@Column(length=30)
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getCredits() {
		return credits;
	}
	public void setCredits(Long credits) {
		this.credits = credits;
	}
	@Column(length=30)
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	// 微信的ipenid 唯一
	@Column(length=30, unique=true)
	public String getOpenId() {
		return openId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Type(type="timestamp")
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getInformWay() {
		return informWay;
	}
	public void setInformWay(int informWay) {
		this.informWay = informWay;
	}
	// 由user表 来维护中间表, 取出所有的角色
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
		joinColumns={
			@JoinColumn(name="userId", referencedColumnName="id")
			},
		inverseJoinColumns={
			@JoinColumn(name="roleId", referencedColumnName="id")
	})
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@OneToMany(mappedBy="toUser")
	public Set<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(Set<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	@OneToMany(mappedBy="fromUser")
	public Set<Message> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(Set<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}
	@OneToMany(mappedBy="author")
	public Set<Topic> getTopics() {
		return topics;
	}
	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	@OneToMany(mappedBy="author")
	public Set<Reply> getReplies() {
		return replies;
	}
	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}
	@OneToMany(mappedBy="operator")
	public Set<RehabAssessment> getRehabAssessmentsMade() {
		return rehabAssessmentsMade;
	}
	public void setRehabAssessmentsMade(Set<RehabAssessment> rehabAssessmentsMade) {
		this.rehabAssessmentsMade = rehabAssessmentsMade;
	}	
}
