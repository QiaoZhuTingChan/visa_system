package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.Group;
import com.jyd.bms.dao.GroupDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("GroupService")
public class GroupService extends BaseService<Group> {
	@Autowired(required = true)
	private GroupDAO groupDao;

	public int getGroupCount(String condition) throws DAOException {
		return groupDao.getGroupCount(condition);
	}

	public List<Group> getPagingGroup(int firstResult, int maxResults, String condition) throws DAOException {
		return groupDao.getPagingGroup(firstResult, maxResults, condition);
	}

	@Override
	public void setDAO() {
		this.baseDAO = groupDao;
	}

}
