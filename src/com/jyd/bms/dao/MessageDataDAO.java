package com.jyd.bms.dao;

import com.jyd.bms.bean.MessageData;
import com.jyd.bms.tool.exception.DAOException;

public interface MessageDataDAO extends HibernateBase<MessageData> {
	/**
	 * 依据Key拿到Value
	 * @param key
	 * @return
	 * @throws DAOException
	 */
	public String getKeyValue(String key) throws DAOException;
}
