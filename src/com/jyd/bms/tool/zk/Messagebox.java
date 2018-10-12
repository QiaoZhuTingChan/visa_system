package com.jyd.bms.tool.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messagebox extends org.zkoss.zul.Messagebox {

	public Messagebox() {
	}

	public static void error(String msg) {
		showMsg(msg, "错误", 1, "z-msgbox z-msgbox-error");
	}

	public static void info(String msg) {
		showMsg(msg, "提醒", 1, "z-msgbox z-msgbox-information");
	}

	public static void warn(String msg) {
		showMsg(msg, "警告", 1, "z-msgbox z-msgbox-exclamation");
	}

	public static int confirm(String msg) {
		return confirm(msg, CONFIRM_LABEL__SYS_DEFINE);
	}

	public static int confirm(String msg, int labelType) {
		return showMsg(msg, "确认", 3, "z-msgbox z-msgbox-question");
	}

	private static int showMsg(String msg, String title, int buttons, String type) {
		return org.zkoss.zul.Messagebox.show(msg, title, buttons, type);		
	}

	public static int CONFIRM_LABEL__USER_DEFINE = 4352;
	public static int CONFIRM_LABEL__SYS_DEFINE = 4096;
	private static final Logger log = LoggerFactory.getLogger(Messagebox.class);

}