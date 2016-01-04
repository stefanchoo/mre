package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * role: admin(管理员) doctor（医生） therapist（治疗师） patient(患者) 
 * @author Administrator
 *
 */
@Entity
@Table(name="_role")
public class Role implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6064997950859557383L;
	
	private Long id;
	private String name;
	// 多对多的关系
	private Set<User> users = new HashSet<User>();
	
	// 多对多的关系
	private Set<Privilege> privileges = new HashSet<Privilege>();
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	// 取出所有的权限
	@ManyToMany(fetch=FetchType.EAGER)
	//由role来维护中间表，即role的增删改查都会影响中间表
	@JoinTable(name="role_privilege",
	joinColumns={
		@JoinColumn(name="roleId", referencedColumnName="id")
	},inverseJoinColumns={
		@JoinColumn(name="privilegeId", referencedColumnName="id")
})
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
}
