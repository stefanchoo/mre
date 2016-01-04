package com.mre.domain;

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
@Table(name="_message")
public class Message implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 162041426174971104L;
	private Long id;
	private String title;
	private String content;
	private Date postTime;
	private boolean readByReceiver;
	private boolean deleteBySender;
	private boolean deleteByReceiver;
	
	// 多对一的关系
	private User fromUser;
	private User toUser;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Type(type="timestamp")
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	public boolean isReadByReceiver() {
		return readByReceiver;
	}
	
	public void setReadByReceiver(boolean readByReceiver) {
		this.readByReceiver = readByReceiver;
	}
	
	public boolean isDeleteBySender() {
		return deleteBySender;
	}
	
	public void setDeleteBySender(boolean deleteBySender) {
		this.deleteBySender = deleteBySender;
	}
	
	public boolean isDeleteByReceiver() {
		return deleteByReceiver;
	}
	
	public void setDeleteByReceiver(boolean deleteByReceiver) {
		this.deleteByReceiver = deleteByReceiver;
	}
	
	@ManyToOne
	@JoinColumn(name="fromUserId")
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	@ManyToOne
	@JoinColumn(name="toUserId")
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
}
