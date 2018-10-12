package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.dao.MenuDAO;
import com.jyd.bms.tool.exception.DAOException;

@Repository
public class MenuDAOImpl extends HibernateBaseTemplate<Menu> implements MenuDAO {

	public int getMenuCount(String condition) throws DAOException {
		String hql = "";
		if (condition.equals("")) {
			hql = "select count(*) from Menu";
			List<Long> lstCount = super.getQueryResult(hql);
			return lstCount.get(0).intValue();
		} else {
			hql = "select count(*) from Menu where name like :condition or menuUrl like :condition or callMethod like :condition";
			@SuppressWarnings("rawtypes")
			Map map = new HashMap();
			map.put("condition", "%" + condition + "%");
			List<Long> lstCount = super.getQueryResult(hql, map);
			return lstCount.get(0).intValue();
		}
	}

	public List<com.jyd.bms.bean.Menu> getAllMenu() throws DAOException {
		String hql = "";
		hql = "from Menu";

		return super.getQueryResult(hql.toString());
	}
	public List<com.jyd.bms.bean.Menu> getAllMenuAndForm() throws DAOException {
		String hql = "";
		hql = "from Menu where display=1";

		return super.getQueryResult(hql.toString());
	}

	public List<Menu> getPagingMenu(int firstResult, int maxResults, String condition)
			throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		if (condition.equals("")) {
			hql = "from Menu";
		} else {
			hql = "from Menu where name like :condition or menuUrl like :condition or callMethod like :condition";
			map.put("condition", "%" + condition + "%");
		}
		return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
	}

	public Menu getParentMenu(Menu menu) throws DAOException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();

		hql = "from Menu where menuId = :menuId";
		map.put("menuId", menu.getParentId());

		List<Menu> listMenu = super.getQueryResult(hql.toString(), map);

		return listMenu.size() == 0 ? null : listMenu.get(0);
	}

}
