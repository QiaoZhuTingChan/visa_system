package com.jyd.bms.bean;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @category Generated 2018-10-12 23:30:12 by GeneratedTool
 * @author mjy
 */
@Entity
public class BaAddress implements Serializable {
	private int id;
	private String contact;// 联系人
	private String contactPhone;// 联系电话
	private String address;// 详细地址
	private String remark;// 备注
	private Timestamp createDate;// 创建时间
	private String createUser;// 创建用户
	private Timestamp updateDate;// 更新时间
	private String updateUser;// 更新用户

	public String getContact() {
		return contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getAddress() {
		return address;
	}

	public String getRemark() {
		return remark;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public int getId() {
		return id;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setId(int id) {
		this.id = id;
	}
}