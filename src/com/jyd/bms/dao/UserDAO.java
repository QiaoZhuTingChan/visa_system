package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.User;
import com.jyd.bms.tool.exception.DAOException;

/**
 * 
 * @author hong
 * @category 用户接口
 */
public interface UserDAO extends HibernateBase<User> {
	public int getUserCount(String condition) throws DAOException;

	public List<User> getPagingUser(int firstResult, int maxResults, String condition) throws DAOException;

	/**
	 * @category 验证用户密码是否正确
	 * @param loginName 用户
	 * @param password  密码
	 * @return 用户
	 * @throws DAOException
	 */
	public User checkLogin(String loginName, String password) throws DAOException;

	/**
	 * @category 依据用户名称查询用户
	 * @param loginName 用户名称
	 * @return 用户
	 * @throws DAOException
	 */
	public User getUserByLoginName(String loginName) throws DAOException;

}
