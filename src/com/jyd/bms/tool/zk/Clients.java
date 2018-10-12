package com.jyd.bms.tool.zk;

import org.zkoss.util.resource.Labels;

public class Clients extends org.zkoss.zk.ui.util.Clients {

	public Clients() {
	}

	public static void showBusy() {
		showBusy(Labels.getLabel("public.msg.showBusy"));
	}

	public static void longOperationComplete() {
		showBusy();
	}
}