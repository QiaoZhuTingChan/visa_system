package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.Group;
import com.jyd.bms.dao.GroupDAO;
import com.jyd.bms.tool.exception.DAOException;

@Repository
public class GroupDAOImpl extends HibernateBaseTemplate<Group> implements GroupDAO {

	public int getGroupCount(String condition) throws DAOException {
		String hql = "";
		if (condition.equals("")) {
			hql = "select count(*) from Group";
			List<Long> lstCount = super.getQueryResult(hql);
			return lstCount.get(0).intValue();
		} else {
			hql = "select count(*) from Group where name like :condition";
			@SuppressWarnings("rawtypes")
			Map map = new HashMap();
			map.put("condition", "%" + condition + "%");
			List<Long> lstCount = super.getQueryResult(hql, map);
			return lstCount.get(0).intValue();
		}
	}

	public List<Group> getPagingGroup(int firstResult, int maxResults, String condition) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		if (condition.equals("")) {
			hql = "from Group";
		} else {
			hql = " from Group where name like :condition";
			map.put("condition", "%" + condition + "%");
		}
		return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
	}
}
