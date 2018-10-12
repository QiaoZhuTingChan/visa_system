package com.jyd.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.MessageData;
import com.jyd.bms.dao.MessageDataDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("MessageDataService")
public class MessageDataService extends BaseService<MessageData> {
	@Autowired(required = true)
	private MessageDataDAO messageDataDAO;

	@Override
	public void setDAO() {
		this.baseDAO = messageDataDAO;
	}

	/**
	 * 依据Key拿到Value
	 * 
	 * @param key
	 * @return
	 * @throws DAOException
	 */
	public String getKeyValue(String key) throws DAOException {
		return messageDataDAO.getKeyValue(key);
	}

}
