package com.jyd.bms.bean;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @category Generated 2018-10-12 22:37:10 by GeneratedTool
 * @author mjy
 */
@Entity
public class BaVisaType implements Serializable {
	private int id;
	private String baVisaType;// 签证类型
	private String remark;// 备注
	private Timestamp createDate;// 创建时间
	private String createUser;// 创建人
	private Timestamp updateDate;// 更新时间
	private String updateUser;// 更新人

	public String getBaVisaType() {
		return baVisaType;
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

	public void setBaVisaType(String baVisaType) {
		this.baVisaType = baVisaType;
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