package com.jyd.bms.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageType implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String messageType;
	private String processClass;
	private String remark;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String createUser;
	private String updateUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getProcessClass() {
		return processClass;
	}
	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 

}
