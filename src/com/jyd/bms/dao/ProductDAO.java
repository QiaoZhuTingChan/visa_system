package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.Product;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-16 21:37:46 by GeneratedTool
 * @author mjy
 */
public interface ProductDAO extends HibernateBase<Product> {
	public int getProductCount(String condition) throws DAOException;
	public List<Product> getPagingProduct(int firstResult, int maxResults, String condition)throws DAOException;
	public List<Product> getAllProduct() throws DAOException;
}