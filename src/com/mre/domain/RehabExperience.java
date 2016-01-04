package com.mre.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_rehabexperience")
public class RehabExperience implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5307003579648111936L;
	private Long id;
	private Date postTime;
	private String title;
	private String content;
	private Patient author;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Type(type="timestamp")
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	@Column(length=60)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Type(type="text")
	@Column(length=65535)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne
	@JoinColumn(name="authorId")
	public Patient getAuthor() {
		return author;
	}
	public void setAuthor(Patient author) {
		this.author = author;
	}

}
