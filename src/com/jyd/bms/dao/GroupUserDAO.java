package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.User;
import com.jyd.bms.tool.exception.DAOException;

public interface GroupUserDAO extends HibernateBase<GroupUser> {

	/**
	 * 依据分组查找用户分组
	 * 
	 * @param group
	 * @return
	 * @throws DAOException
	 */
	public List<User> getGroupUserByGroup(Group group) throws DAOException;

	/**
	 * 依据用户和分组查找用户分组
	 * 
	 * @param group
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public GroupUser findGroupUser(Group group, User user) throws DAOException;

	/**
	 * 依据用户查找用户分组
	 * 
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public List<GroupUser> getGroupUserByUser(User user) throws DAOException;
}
