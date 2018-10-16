package com.jyd.bms.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * @category Generated 2018-10-16 21:37:46 by GeneratedTool
 * @author mjy
 */
@Entity
public class Product implements Serializable {
	private int id;
	private String productName;// 产品名称
	private double lineCustomerPrice;// 直客价
	private double peerPrice;// 同行价
	private double marketPrice;// 市场价
	private String img;// 产品图
	private int baVisaTypeId;// 签证类型
	private int baAdmissibleAreaId;// 受理领区
	private String admissibleAreaType;// 领区划分
	private String outCycle;// 出签周期
	private String residenceTime;// 停留时间
	private String effectiveTime;// 有效时间
	private int entryTimes;// 入境次数
	private boolean faceSign;// 是否面签
	private String remark;// 备注
	private Timestamp createDate;// 创建时间
	private String createUser;// 创建人
	private Timestamp updateDate;// 更新时间
	private String updateUser;// 更新人

	public double getLineCustomerPrice() {
		return lineCustomerPrice;
	}

	public void setLineCustomerPrice(double lineCustomerPrice) {
		this.lineCustomerPrice = lineCustomerPrice;
	}

	public double getPeerPrice() {
		return peerPrice;
	}

	public void setPeerPrice(double peerPrice) {
		this.peerPrice = peerPrice;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public boolean isFaceSign() {
		return faceSign;
	}

	public void setFaceSign(boolean faceSign) {
		this.faceSign = faceSign;
	}

	public String getProductName() {
		return productName;
	}

	public String getImg() {
		return img;
	}

	public int getBaVisaTypeId() {
		return baVisaTypeId;
	}

	public int getBaAdmissibleAreaId() {
		return baAdmissibleAreaId;
	}

	public String getAdmissibleAreaType() {
		return admissibleAreaType;
	}

	public String getOutCycle() {
		return outCycle;
	}

	public String getResidenceTime() {
		return residenceTime;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public int getEntryTimes() {
		return entryTimes;
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

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setBaVisaTypeId(int baVisaTypeId) {
		this.baVisaTypeId = baVisaTypeId;
	}

	public void setBaAdmissibleAreaId(int baAdmissibleAreaId) {
		this.baAdmissibleAreaId = baAdmissibleAreaId;
	}

	public void setAdmissibleAreaType(String admissibleAreaType) {
		this.admissibleAreaType = admissibleAreaType;
	}

	public void setOutCycle(String outCycle) {
		this.outCycle = outCycle;
	}

	public void setResidenceTime(String residenceTime) {
		this.residenceTime = residenceTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public void setEntryTimes(int entryTimes) {
		this.entryTimes = entryTimes;
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