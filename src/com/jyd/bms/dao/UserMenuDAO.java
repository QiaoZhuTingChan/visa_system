package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.bean.UserMenu;
import com.jyd.bms.tool.exception.DAOException;
/**
 * 
 * @author hong
 * @category 用户菜单接口
 */
public interface UserMenuDAO extends HibernateBase<UserMenu> {
	public List<UserMenu> getUserMenu(User user) throws DAOException;

	/**
	 * @category 依据用户和菜单检查用户权限
	 * @param user　用户
	 * @param menu　菜单
	 * @return　用户菜单集合
	 * @throws DAOException
	 */
	public List<UserMenu> checkUserMenuPower(User user, Menu menu) throws DAOException;
}
