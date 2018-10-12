package com.jyd.bms.message;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.Message;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;

public class PublicMessage implements MessageInterface {

	@Override
	public Menu process(Message message) throws NumberFormatException, DataNotFoundException, DAOException {
		Menu menu = new Menu();
		menu.setMenuUrl("/message/publicMessage.zul?messageId=" + message.getId());
		menu.setName("公告");
		return menu;
	}

}
