package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.User;
import com.jyd.bms.dao.UserDAO;
import com.jyd.bms.tool.exception.DAOException;

@Repository
public class UserDAOImpl extends HibernateBaseTemplate<User> implements UserDAO {

	public int getUserCount(String condition) throws DAOException {
		String hql = "";
		if (condition.equals("")) {
			hql = "select count(*) from User";
			List<Long> lstCount = super.getQueryResult(hql);
			return lstCount.get(0).intValue();
		} else {
			hql = "select count(*) from User where loginName like :condition or userName like :condition";
			@SuppressWarnings("rawtypes")
			Map map = new HashMap();
			map.put("condition", "%" + condition + "%");
			List<Long> lstCount = super.getQueryResult(hql, map);
			return lstCount.get(0).intValue();
		}
	}

	public List<User> getPagingUser(int firstResult, int maxResults, String condition) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		if (condition.equals("")) {
			hql = "from User";
		} else {
			hql = "from User where loginName like :condition or userName like :condition";
			map.put("condition", "%" + condition + "%");
		}
		return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
	}

	public User checkLogin(String loginName, String password) throws DAOException {
		String hql = "";

		hql = "from User where loginName = :loginName and password = :password";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("loginName", loginName);
		map.put("password", password);
		List<User> listUser = super.getQueryResult(hql, map);

		if (listUser.size() == 0)
			return null;
		else
			return listUser.get(0);

	}

	public User getUserByLoginName(String loginName) throws DAOException {
		String hql = "";

		hql = "from User where loginName = :loginName";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("loginName", loginName);
		List<com.jyd.bms.bean.User> listUser = super.getQueryResult(hql, map);

		if (listUser.size() == 0)
			return null;
		else
			return listUser.get(0);
	}

}
