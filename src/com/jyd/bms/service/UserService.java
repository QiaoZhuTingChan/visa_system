package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.User;
import com.jyd.bms.dao.UserDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("UserService")
public class UserService extends BaseService<User> {
	@Autowired(required = true)
	private UserDAO userDao;

	public int getUserCount(String condition) throws DAOException {
		return userDao.getUserCount(condition);
	}

	public List<User> getPagingUser(int firstResult, int maxResults, String condition) throws DAOException {
		return userDao.getPagingUser(firstResult, maxResults, condition);
	}

	public User checkLogin(String loginName, String password) throws DAOException {
		return userDao.checkLogin(loginName, password);
	}

	public User getUserByLoginName(String loginName) throws DAOException {
		return userDao.getUserByLoginName(loginName);
	}

	@Override
	public void setDAO() {
		this.baseDAO = userDao;
	}
}
