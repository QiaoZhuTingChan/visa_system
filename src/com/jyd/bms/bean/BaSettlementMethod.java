package com.jyd.bms.bean;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @category Generated 2018-10-15 22:37:08 by GeneratedTool
 * @author mjy
 */
@Entity
public class BaSettlementMethod implements Serializable {
private int id;
private String baSettlementMethod;// 结算方式名称
private Timestamp createDate;// 创建时间
private String createUser;// 创建用户
private Timestamp updateDate;// 更新时间
private String updateUser;// 更新用户
private String remark;// 备注
public String getBaSettlementMethod(){
 return baSettlementMethod;
}

public Timestamp getCreateDate(){
 return createDate;
}

public String getCreateUser(){
 return createUser;
}

public Timestamp getUpdateDate(){
 return updateDate;
}

public String getUpdateUser(){
 return updateUser;
}

public String getRemark(){
 return remark;
}

public int getId() {
 return id;
}
public void setBaSettlementMethod(String baSettlementMethod){
 this.baSettlementMethod=baSettlementMethod;
}

public void setCreateDate(Timestamp createDate){
 this.createDate=createDate;
}

public void setCreateUser(String createUser){
 this.createUser=createUser;
}

public void setUpdateDate(Timestamp updateDate){
 this.updateDate=updateDate;
}

public void setUpdateUser(String updateUser){
 this.updateUser=updateUser;
}

public void setRemark(String remark){
 this.remark=remark;
}

public void setId(int id) {
 this.id = id;
}
}