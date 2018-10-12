package com.jyd.bms.message;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.Message;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;

public interface MessageInterface {
	public Menu process(Message message) throws NumberFormatException, DataNotFoundException, DAOException;
}
