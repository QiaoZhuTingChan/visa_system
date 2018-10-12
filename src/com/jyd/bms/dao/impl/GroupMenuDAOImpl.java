package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.dao.GroupMenuDAO;
import com.jyd.bms.tool.exception.DAOException;

@Repository
public class GroupMenuDAOImpl extends HibernateBaseTemplate<GroupMenu> implements GroupMenuDAO {

	public List<GroupMenu> getGroupMenu(Group group) throws DAOException {
		String hql = " from GroupMenu where group = :group";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("group", group);

		return super.getQueryResult(hql.toString(), map);
	}

	public com.jyd.bms.bean.GroupMenu findGroupMenu(Group group, Menu menu) throws DAOException {
		String hql = " from GroupMenu where group = :group and menuId=:menuId";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("group", group);
		map.put("menuId", menu.getMenuId());

		return (com.jyd.bms.bean.GroupMenu) super.getUniqueResult(hql.toString(), map);
	}

}
