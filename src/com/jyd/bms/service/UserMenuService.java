package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.bean.UserMenu;
import com.jyd.bms.dao.UserMenuDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("UserMenuService")
public class UserMenuService extends BaseService<UserMenu> {
	@Autowired(required = true)
	private UserMenuDAO userMenuDao;

	public List<UserMenu> getUserMenu(User user) throws DAOException {
		return userMenuDao.getUserMenu(user);
	}

	public List<UserMenu> checkUserMenuPower(User user, Menu menu) throws DAOException {
		return userMenuDao.checkUserMenuPower(user, menu);
	}

	@Override
	public void setDAO() {
		this.baseDAO = userMenuDao;
	}
}
