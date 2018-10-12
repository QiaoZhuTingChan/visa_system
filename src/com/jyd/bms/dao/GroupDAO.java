package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.Group;
import com.jyd.bms.tool.exception.DAOException;

public interface GroupDAO extends HibernateBase<Group> {
	/**
	 * 查询
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public int getGroupCount(String condition) throws DAOException;

	/**
	 * 分页
	 * @param firstResult
	 * @param maxResults
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public List<Group> getPagingGroup(int firstResult, int maxResults, String condition) throws DAOException;
}
