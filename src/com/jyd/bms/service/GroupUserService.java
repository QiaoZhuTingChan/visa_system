package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.User;
import com.jyd.bms.dao.GroupUserDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("GroupUserService")
public class GroupUserService extends BaseService<GroupUser> {
	@Autowired(required = true)
	private GroupUserDAO groupUserDao;

	public List<User> getGroupUserByGroup(Group group) throws DAOException {
		return groupUserDao.getGroupUserByGroup(group);
	}

	public GroupUser findGroupUser(Group group, User user) throws DAOException {
		return groupUserDao.findGroupUser(group, user);
	}

	public List<GroupUser> getGroupUserByUser(User user) throws DAOException {
		return groupUserDao.getGroupUserByUser(user);
	}

	@Override
	public void setDAO() {
		this.baseDAO = groupUserDao;
	}

}
