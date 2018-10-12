package com.jyd.bms.bean;

import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
@Entity
public class Menu implements Serializable {
	private int id;
	private String menuId;
	private String parentId;
	private String name;
	private int type;
	private String iconName;
	private String menuUrl;
	private String callMethod;
	private String languageId;
	private Boolean display;
	private int Sort;
	private boolean checked;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getCallMethod() {
		return callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public int getSort() {
		return Sort;
	}

	public void setSort(int sort) {
		Sort = sort;
	}

	public boolean equalsIfIdNull(Object other) {
		Menu castOther = (Menu) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCodeIfIdNull() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
