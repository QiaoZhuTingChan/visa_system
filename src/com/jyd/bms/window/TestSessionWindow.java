package com.jyd.bms.window;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;

import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;

public class TestSessionWindow extends BaseWindow {
	private Button postSession;
	private Button getSession;
	Session session = Sessions.getCurrent();

	public TestSessionWindow() {
		this.menuId = "index";
	}

	@Override
	public void initUI() {

	}

	@Override
	public void initData() {

	}

	public void onClick$postSession() {
		session.setAttribute("user", "xiaomi");
		Messagebox.show("设置session,user:xiaomi");
	}

	public void onClick$getSession() {
		Messagebox.show(session.getAttribute("user").toString());
	}
}
