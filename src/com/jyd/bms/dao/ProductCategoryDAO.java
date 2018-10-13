package com.jyd.bms.dao;

import java.util.List;

import com.jyd.bms.bean.ProductCategory;
import com.jyd.bms.tool.exception.DAOException;

/**
 * @category Generated 2018-10-13 14:23:15 by GeneratedTool
 * @author mjy
 */
public interface ProductCategoryDAO extends HibernateBase<ProductCategory> {

	public int getProductCategoryCount(String condition) throws DAOException;

	public List<ProductCategory> getPagingProductCategory(int firstResult, int maxResults, String condition)
			throws DAOException;

	public List<ProductCategory> getAllProductCategory() throws DAOException;
}