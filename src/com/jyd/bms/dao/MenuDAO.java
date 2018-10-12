package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.tool.exception.DAOException;

public interface MenuDAO extends HibernateBase<Menu> {
	/**
	 * 查询菜单
	 * 
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public int getMenuCount(String condition) throws DAOException;

	/**
	 * 分页
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public List<Menu> getPagingMenu(int firstResult, int maxResults, String condition) throws DAOException;

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<Menu> getAllMenu() throws DAOException;

	/**
	 * 查询父菜单
	 * 
	 * @param menu
	 * @return
	 * @throws DAOException
	 */
	public Menu getParentMenu(Menu menu) throws DAOException;
	
	/**
	 * @category 查看所有的菜单包括表单环节
	 * @return
	 * @throws DAOException
	 */
	public List<com.jyd.bms.bean.Menu> getAllMenuAndForm() throws DAOException ;
}
