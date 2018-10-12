package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.dao.MenuDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("MenuService")
public class MenuService extends BaseService<Menu> {
	@Autowired(required = true)
	private MenuDAO menuDao;

	public int getMenuCount(String condition) throws DAOException {
		return menuDao.getMenuCount(condition);
	}

	public List<Menu> getPagingMenu(int firstResult, int maxResults, String condition) throws DAOException {
		return menuDao.getPagingMenu(firstResult, maxResults, condition);
	}

	public List<Menu> getAllMenu() throws DAOException {
		return menuDao.getAllMenu();
	}

	public Menu getParentMenu(Menu menu) throws DAOException {
		return menuDao.getParentMenu(menu);
	}

	public List<com.jyd.bms.bean.Menu> getAllMenuAndForm() throws DAOException {
		return menuDao.getAllMenuAndForm();
	}

	
	@Override
	public void setDAO() {
		this.baseDAO = menuDao;
	}
}
