package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.tool.exception.DAOException;

public interface GroupMenuDAO extends HibernateBase<GroupMenu> {
	/**
	 * 查询分组菜单
	 * @param group
	 * @return
	 * @throws DAOException
	 */
	public List<GroupMenu> getGroupMenu(Group group) throws DAOException;

	public com.jyd.bms.bean.GroupMenu findGroupMenu(Group group, Menu menu) throws DAOException;
}
