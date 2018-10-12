package com.jyd.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.dao.GroupMenuDAO;
import com.jyd.bms.tool.exception.DAOException;

@Service("GroupMenuService")
public class GroupMenuService extends BaseService<GroupMenu> {
	@Autowired(required = true)
	private GroupMenuDAO groupMenuDao;

	public List<GroupMenu> getGroupMenu(Group group) throws DAOException {
		return groupMenuDao.getGroupMenu(group);
	}

	public com.jyd.bms.bean.GroupMenu findGroupMenu(Group group, Menu menu) throws DAOException {
		return groupMenuDao.findGroupMenu(group, menu);
	}

	@Override
	public void setDAO() {
		this.baseDAO = groupMenuDao;
	}

}
