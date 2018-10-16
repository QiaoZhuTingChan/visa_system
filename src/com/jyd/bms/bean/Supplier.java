package com.jyd.bms.bean;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @category Generated 2018-10-15 22:39:51 by GeneratedTool
 * @author mjy
 */
@Entity
public class Supplier implements Serializable {
private int id;
private String contactName;// 联系人
private String address;// 地址
private String company;// 所属公司
private String contactPhone;// 联系方式
private String remark;// 备注
private Timestamp createDate;// 创建时间
private String createUser;// 创建人
private Timestamp updateDate;// 更新时间
private String updateUser;// 更新人
public String getContactName(){
 return contactName;
}

public String getAddress(){
 return address;
}

public String getCompany(){
 return company;
}

public String getContactPhone(){
 return contactPhone;
}

public String getRemark(){
 return remark;
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

public int getId() {
 return id;
}
public void setContactName(String contactName){
 this.contactName=contactName;
}

public void setAddress(String address){
 this.address=address;
}

public void setCompany(String company){
 this.company=company;
}

public void setContactPhone(String contactPhone){
 this.contactPhone=contactPhone;
}

public void setRemark(String remark){
 this.remark=remark;
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

public void setId(int id) {
 this.id = id;
}
}