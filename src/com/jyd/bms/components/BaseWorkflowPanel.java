package com.jyd.bms.components;

import java.util.HashMap;

import org.zkoss.zul.Panel;

public abstract class BaseWorkflowPanel extends Panel {
	public abstract void initUI();

	public abstract void initComponent();

	public abstract void setMap(HashMap<String, Object> map);
	
}
