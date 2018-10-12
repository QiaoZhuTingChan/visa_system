package com.jyd.bms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.User;
import com.jyd.bms.dao.GroupUserDAO;
import com.jyd.bms.tool.exception.DAOException;
@Repository
public class GroupUserDAOImpl extends HibernateBaseTemplate<GroupUser> implements GroupUserDAO{
	public List<GroupUser> getGroupUserByUser(User user) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();

		hql = " from GroupUser where user = :user";
		map.put("user", user);

		return super.getQueryResult(hql.toString(), map);
	}

	public List<User> getGroupUserByGroup(Group group) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();

		hql = " from GroupUser where group = :group";
		map.put("group", group);
		List<GroupUser> list = super.getQueryResult(hql.toString(), map);

		List<User> listUser = new ArrayList<User>();

		for (com.jyd.bms.bean.GroupUser groupUser : list) {
			listUser.add(groupUser.getUser());
		}

		return listUser;
	}

	public GroupUser findGroupUser(Group group, User user) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();

		hql = " from GroupUser where group = :group and user=:user";
		map.put("group", group);
		map.put("user", user);
		return (GroupUser) super.getUniqueResult(hql.toString(), map);
	}

}
