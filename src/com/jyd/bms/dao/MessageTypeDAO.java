package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.MessageType;
import com.jyd.bms.tool.exception.DAOException;

public interface MessageTypeDAO extends HibernateBase<MessageType> {
	public int getMessageTypeCount(String condition) throws DAOException;
	
	public List<MessageType> getPagingMessageType(int firstResult, int maxResults, String condition) throws DAOException;
	
	public List<MessageType> getAllMessageType() throws DAOException;

}
