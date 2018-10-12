package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.bean.UserMenu;
import com.jyd.bms.dao.UserMenuDAO;
import com.jyd.bms.tool.exception.DAOException;
@Repository
public class UserMenuDAOImpl extends HibernateBaseTemplate<UserMenu> implements UserMenuDAO {
	public List<UserMenu> getUserMenu(User user) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();

		hql = "from UserMenu where user =:user";
		map.put("user", user);

		return super.getQueryResult(hql.toString(), map);
	}

	public List<UserMenu> checkUserMenuPower(User user, Menu menu) throws DAOException {
		// TODO Auto-generated method stub
		String hql = "";
		hql = "from UserMenu where user_id = :userId and menu_id = :menuId";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("userId", user.getId());
		map.put("menuId", menu.getMenuId());
		List<UserMenu> listUserMenu = super.getQueryResult(hql, map);
		if (listUserMenu.size() == 0)
			return null;
		else
			return listUserMenu;
	}
}
