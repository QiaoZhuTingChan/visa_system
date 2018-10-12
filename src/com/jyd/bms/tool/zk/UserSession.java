package com.jyd.bms.tool.zk;

import org.zkoss.zk.ui.Sessions;

import com.jyd.bms.bean.User;

public class UserSession {
	public static User getUser()
	{
		org.zkoss.zk.ui.Session session= Sessions.getCurrent();
		if( session.getAttribute("User") == null )
			return null;
		User user = (User)session.getAttribute("User");
		return user;
	}
}
