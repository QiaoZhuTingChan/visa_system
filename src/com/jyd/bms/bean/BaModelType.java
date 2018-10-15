package com.jyd.bms.bean;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @category Generated 2018-10-14 19:51:29 by GeneratedTool
 * @author mjy
 */
@Entity
public class BaModelType implements Serializable {
private int id;
private String baModelType;// 职位名称
private Timestamp createDate;// 创建时间
private String createUser;// 创建用户
private Timestamp updateDate;// 更新时间
private String updateUser;// 更新用户
private String remark;// 备注
public String getBaModelType(){
 return baModelType;
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
public void setBaModelType(String baModelType){
 this.baModelType=baModelType;
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