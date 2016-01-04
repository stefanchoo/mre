package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="_privilege")
public class Privilege implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8493560963990705732L;
	private Long id;
	private String name;
	private String url;
	
	// 多对多的关系
	private Set<Role> roles = new HashSet<Role>();
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(length=60)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=60)
	public String getUrl() {
		return url;
	}
	@Column(length=60)
	public void setUrl(String url) {
		this.url = url;
	}  
	
	@ManyToMany(mappedBy="privileges")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
