package com.jyd.bms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyd.bms.bean.ProductCategory;
import com.jyd.bms.dao.ProductCategoryDAO;
import com.jyd.bms.tool.exception.DAOException;

/**
 * @category Generated 2018-10-13 14:23:15 by GeneratedTool
 * @author mjy
 */
@Repository
public class ProductCategoryDAOImpl extends HibernateBaseTemplate<ProductCategory> implements ProductCategoryDAO {

	public int getProductCategoryCount(String condition) throws DAOException {
		String hql = "";
		if (condition.equals("")) {
			hql = "select count(*) from ProductCategory";
			List<Long> lstCount = super.getQueryResult(hql);
			return lstCount.get(0).intValue();
		} else {
			hql = "select count(*) from ProductCategory where name like :condition";
			Map map = new HashMap();
			map.put("condition", "%" + condition + "%");
			List<Long> lstCount = super.getQueryResult(hql, map);
			return lstCount.get(0).intValue();
		}
	}

	public List<ProductCategory> getPagingProductCategory(int firstResult, int maxResults, String condition)
			throws DAOException {
		String hql = "";
		Map map = new HashMap();
		if (condition.equals("")) {
			hql = "from ProductCategory";
		} else {
			hql = "from ProductCategory where name like :condition";
			map.put("condition", "%" + condition + "%");
		}
		return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
	}

	public List<ProductCategory> getAllProductCategory() throws DAOException {
		String hql = "";
		hql = "from ProductCategory";
		return super.getQueryResult(hql.toString());
	}

}
