package com.jyd.bms.tool.zk;

import java.io.File;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

import com.jyd.bms.tool.exception.DAOException;

public abstract class GenericForwardWindow extends Window implements AfterCompose {

	public GenericForwardWindow() {
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void firstLoad() throws DAOException {
		initUI();
		initData();
	}

	public abstract void initUI();

	public abstract void initData() throws DAOException;

	public void longOperationComplete() {
		Clients.longOperationComplete();
	}

	public void addTempFile(File file) {
		TempFileUtils.addTempFile(file);
	}

	public void addTempFile(List files) {
		TempFileUtils.addTempFile(files);
	}
}