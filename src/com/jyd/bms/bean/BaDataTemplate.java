package com.jyd.bms.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * @category Generated 2018-10-14 20:12:29 by GeneratedTool
 * @author mjy
 */
@Entity
public class BaDataTemplate implements Serializable {
	private int id;
	private String src;// 模板文档
	private String baDataTemplate;// 名称
	private String remark;// 备注
	private Timestamp createDate;// 创建时间
	private String createUser;// 创建用户
	private Timestamp updateDate;// 更新时间
	private String updateUser;// 更新用户
	private BaModelType baModelType;

	public BaModelType getBaModelType() {
		return baModelType;
	}

	public void setBaModelType(BaModelType baModelType) {
		this.baModelType = baModelType;
	}

	public String getSrc() {
		return src;
	}

	public String getBaDataTemplate() {
		return baDataTemplate;
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

	public void setSrc(String src) {
		this.src = src;
	}

	public void setBaDataTemplate(String baDataTemplate) {
		this.baDataTemplate = baDataTemplate;
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