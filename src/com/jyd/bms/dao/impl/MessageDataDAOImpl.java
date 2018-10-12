package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.MessageData;
import com.jyd.bms.dao.MessageDataDAO;
import com.jyd.bms.tool.exception.DAOException;

@Repository
public class MessageDataDAOImpl extends HibernateBaseTemplate<MessageData> implements MessageDataDAO {

	/**
	 * 依据Key拿到Value
	 * @param key
	 * @return
	 * @throws DAOException
	 */
	public String getKeyValue(String key) throws DAOException {
		String hql = "";
		hql = "select value from MessageData where key = :key";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("key", key);
		List<String> list = super.getQueryResult(hql, map);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	

}
