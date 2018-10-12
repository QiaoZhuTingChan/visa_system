package com.jyd.bms.dto;

import org.zkoss.zul.Checkbox;

import com.jyd.bms.bean.User;

public class UserCheckbox {
	private Checkbox checkbox;
	private User user;
	public Checkbox getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Checkbox checkbox) {
		this.checkbox = checkbox;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
